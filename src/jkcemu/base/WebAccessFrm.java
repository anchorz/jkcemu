/*
 * (c) 2017-2017 Andreas Ziermann
 *
 * Kleincomputer-Emulator
 *
 * Ladefunktion, Zugriff auf die Z1013 Software Datenbank.
 */

package jkcemu.base;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.table.JTableHeader;
import javax.swing.table.DefaultTableCellRenderer;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import jkcemu.Main;

public class WebAccessFrm extends JDialog {

    private static final long serialVersionUID = 8159341847212023786L;

    private static final String databasePrefix = "http://z1013.mrboot.de/software-database/db/";

    private TableRowSorter<MyTableModel> sorter;

    private Vector<String[]> data;

    class MyTableModel extends AbstractTableModel {
        private static final long serialVersionUID = 7657998082206360204L;
        private String[] columnNames = { "MD5", "AAdr", "EAdr", "SAdr", "Typ",
                "NAME", "Beschreibung" };
        private Vector<String[]> data;

        MyTableModel(Vector<String[]> data) {
            this.data = data;
        }

        public String getColumnName(int col) {
            return columnNames[col];
        }

        public int getColumnCount() {
            return columnNames.length;
        }

        public int getRowCount() {
            return data.size();
        }

        public Object getValueAt(int row, int col) {
            return data.elementAt(row)[col];
        }

        @SuppressWarnings("unchecked")
        public Class<? extends String> getColumnClass(int c) {
            return (Class<? extends String>) getValueAt(0, c).getClass();
        }
    }

    JFrame owner;
    StatusUpdateListener statusUpdateListener;
    EmuThread emuThread;

    WebAccessFrm(JFrame owner, StatusUpdateListener statusUpdateListener,
            EmuThread emuThread) {
        super(owner);
        this.owner = owner;
        this.statusUpdateListener = statusUpdateListener;
        this.emuThread = emuThread;
        getContentPane().setLayout(new BorderLayout());
        this.setTitle("Zugriff auf die Z1013 Software Datenbank");
    }

    protected JRootPane createRootPane() {
        JRootPane rootPane = new JRootPane();
        KeyStroke stroke = KeyStroke.getKeyStroke("ESCAPE");
        Action actionListener = new AbstractAction() {
            private static final long serialVersionUID = -2221563287411606852L;

            public void actionPerformed(ActionEvent actionEvent) {
                setVisible(false);
            }
        };
        InputMap inputMap = rootPane
                .getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(stroke, "ESCAPE");
        rootPane.getActionMap().put("ESCAPE", actionListener);

        return rootPane;
    }

    JTextField searchTerm;
    JTable table;

    static final int COL_AADR = 1;
    static final int COL_EADR = 2;
    static final int COL_SADR = 3;
    static final int COL_LINK = 7;

    void download() {
        data = new Vector<String[]>();
        // Vector<String> linkList = new Vector<String>();

        try {
            Document doc = Jsoup.connect(
                    "http://z1013.mrboot.de/software-database/db/list.xml")
                    .get();
            Elements fileList = doc.select("filelist");
            Elements links = fileList.select("file");
            for (Element link : links) {
                String[] line = new String[8];
                String ref = link.attr("link");
                line[0] = ref.substring(0, 32);
                line[COL_AADR] = link.attr("aadr");
                line[COL_EADR] = link.attr("eadr");
                line[COL_SADR] = link.attr("sadr");
                line[4] = link.attr("typ");
                line[5] = link.attr("name");
                line[6] = link.attr("kurz");
                line[COL_LINK] = ref;
                data.addElement(line);
            }
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
        }

        setModalityType(Dialog.ModalityType.APPLICATION_MODAL);

        JPanel searchField=new JPanel(new BorderLayout());
        searchTerm = new JTextField();
        searchTerm.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                newFilter();
            }

            public void insertUpdate(DocumentEvent e) {
                newFilter();
            }

            public void removeUpdate(DocumentEvent e) {
                newFilter();
            }
        });
        
        JLabel btnCut = new JLabel();
        Image img=Main.getImage( this, "/images/extra/magnifying-glass.png");
        Image newimg = img.getScaledInstance(24, 24,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
        ImageIcon iconLogo = new ImageIcon(newimg);
        btnCut.setIcon(iconLogo);
        btnCut.setBorder(BorderFactory.createEmptyBorder(0,0,0,4));
        searchTerm.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 16));

        searchTerm.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                int currentRow = table.getSelectedRow();
                int row = table.convertRowIndexToModel(currentRow);
                loadFile(data.elementAt(row));
            }
        });
        searchField.add(btnCut,BorderLayout.WEST);
        searchField.add(searchTerm,BorderLayout.CENTER);
        searchField.setBorder(BorderFactory.createEmptyBorder(4,4,4,4));
                
        getContentPane().add(searchField, BorderLayout.NORTH);

        // Create a table with a sorter.
        MyTableModel model = new MyTableModel(data);
        sorter = new TableRowSorter<MyTableModel>(model);
        table = new JTable(model);
        searchTerm.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
                dispatch(e);
            }

            private void dispatch(KeyEvent e) {
                switch (e.getKeyCode()) {
                case KeyEvent.VK_DOWN:
                case KeyEvent.VK_UP:
                case KeyEvent.VK_PAGE_DOWN:
                case KeyEvent.VK_PAGE_UP:
                    table.dispatchEvent(e);
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                dispatch(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                dispatch(e);
            }
        });
        table.setFocusable(false);
        table.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 16));
        table.setRowSorter(sorter);
        // table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setFillsViewportHeight(true);
        // table.setPreferredSize(new Dimension (1300, 500));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getColumnModel().getColumn(0).setPreferredWidth(100);
        table.getColumnModel().getColumn(1).setPreferredWidth(40);
        table.getColumnModel().getColumn(2).setPreferredWidth(40);
        table.getColumnModel().getColumn(3).setPreferredWidth(40);
        table.getColumnModel().getColumn(4).setPreferredWidth(30);
        table.getColumnModel().getColumn(5).setPreferredWidth(180);
        table.getColumnModel().getColumn(6).setPreferredWidth(450);
        table.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
                JTable table = (JTable) me.getSource();
                int row = table.getSelectedRow();
                row = table.convertRowIndexToModel(row);
                if (me.getClickCount() == 2) {
                    loadFile(data.elementAt(row));
                }
            }
        });

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int x = 0; x < 5; x++) {
            table.getColumnModel().getColumn(x).setCellRenderer(centerRenderer);
        }

        JPanel panel = new JPanel(new BorderLayout());
        JTableHeader header = table.getTableHeader();
        panel.add(header, BorderLayout.NORTH);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.setBorder(BorderFactory.createEmptyBorder(0,4,4,4));

        scrollPane.setPreferredSize(new Dimension(1000, 500));
        getContentPane().add(panel, BorderLayout.CENTER);
        pack();
        setVisible(true);

    }

    private void loadFile(String[] entry) {
        String url = databasePrefix + entry[COL_LINK];
        int aadr = Integer.parseInt(entry[COL_AADR], 16);
        int eadr = Integer.parseInt(entry[COL_EADR], 16);
        int sadr = Integer.parseInt(entry[COL_SADR], 16);
        try {
            byte[] bytes = Jsoup.connect(url).ignoreContentType(true).execute()
                    .bodyAsBytes();
            // Nur beim Z1013!
            // for( int i = 0; i < 32; i++ ) {
            // emuThread.setMemByte(
            // Z1013.MEM_HEAD + i,
            // bytes[i] );
            // }
            for (int i = 0; i < bytes.length - 32; i++) {
                emuThread.setMemByte(aadr + i, bytes[i + 32]);
            }
            if (eadr > 0xFFFF) {
                eadr = 0xFFFF;
            }
            statusUpdateListener.showStatusText(
                    String.format("Datei nach %04X-%04X (Start:%04X) geladen",
                            aadr, eadr, sadr));
            dispose();

        } catch (IOException ex) {
            ex.printStackTrace(System.out);
        }
    }

    private void newFilter() {
        RowFilter<MyTableModel, Object> rf = null;
        // If current expression doesn't parse, don't update.
        try {
            rf = RowFilter.regexFilter("(?i)" + searchTerm.getText(), 0, 5, 6);
        } catch (java.util.regex.PatternSyntaxException e) {
            return;
        }
        sorter.setRowFilter(rf);
    }

}
