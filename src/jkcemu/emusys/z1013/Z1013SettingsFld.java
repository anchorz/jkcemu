/*
 * (c) 2010-2017 Jens Mueller
 *
 * Kleincomputer-Emulator
 *
 * Komponente fuer die Z1013-Einstellungen
 */

package jkcemu.emusys.z1013;

import java.awt.Component;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.EventObject;
import java.util.Properties;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JSeparator;
import jkcemu.base.AbstractSettingsFld;
import jkcemu.base.AutoInputSettingsFld;
import jkcemu.base.AutoLoadSettingsFld;
import jkcemu.base.BaseDlg;
import jkcemu.base.EmuThread;
import jkcemu.base.EmuUtil;
import jkcemu.base.RAMFloppiesSettingsFld;
import jkcemu.base.RAMFloppy;
import jkcemu.base.ROMFileSettingsFld;
import jkcemu.base.SettingsFrm;
import jkcemu.base.UserCancelException;
import jkcemu.base.UserInputException;
import jkcemu.disk.GIDESettingsFld;
import jkcemu.emusys.Z1013;

public class Z1013SettingsFld extends AbstractSettingsFld {

    private static final long serialVersionUID = 6572193917954247769L;

    private JTabbedPane tabbedPane;
    private JRadioButton btnZ1013_01;
    private JRadioButton btnZ1013_12;
    private JRadioButton btnZ1013_16;
    private JRadioButton btnZ1013_64;
    private JRadioButton btnZ1013_128;
    private JRadioButton btnMon202;
    private JRadioButton btnMonA2;
    private JRadioButton btnMonRB_K7659;
    private JRadioButton btnMonRB_S6009;
    private JRadioButton btnMonINCOM_K7669;
    private JRadioButton btnMonJM_1992;
    private JRadioButton btnBL4_K7659;
    private JRadioButton btnPortNone;
    private JRadioButton btnPortJoyJuTe_6_87;
    private JRadioButton btnPortJoyPractic_4_87;
    private JRadioButton btnPortJoyPractic_1_88;
    private JRadioButton btnPortCentr7Practic_2_89;
    private JRadioButton btnPortCentr8FA_10_90;
    private JCheckBox btnModBasic;
    private JCheckBox btnModMegaROM;
    private JCheckBox btnFloppyDisk;
    private JCheckBox btnRTC;
    private JCheckBox btnKCNet;
    private JCheckBox btnVDIP;
    private JCheckBox btnGraphCCJ;
    private JCheckBox btnGraphic;
    private JCheckBox btnCatchPrintCalls;
    private JCheckBox btnCatchJoyCalls;
    private JCheckBox btnPasteFast;
    private ROMFileSettingsFld fldAltOS;
    private ROMFileSettingsFld fldAltFont;
    private ROMFileSettingsFld fldAltGCCJFont;
    private ROMFileSettingsFld fldAltBasic;
    private ROMFileSettingsFld fldMegaROM;
    private JPanel tabModel;
    private JPanel tabMon;
    private JPanel tabUserPort;
    private RAMFloppiesSettingsFld tabRF;
    private GIDESettingsFld tabGIDE;
    private JPanel tabModROM;
    private JPanel tabExt;
    private JPanel tabEtc;
    private AutoLoadSettingsFld tabAutoLoad;
    private AutoInputSettingsFld tabAutoInput;
    private jkcemu.emusys.z1013.v128.OptionPanel v128ExtensionsPanel;

    public Z1013SettingsFld(SettingsFrm settingsFrm, String propPrefix) {
        super(settingsFrm, propPrefix);

        setLayout(new BorderLayout());

        this.tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        add(this.tabbedPane, BorderLayout.CENTER);

        // Tab Modell
        this.tabModel = new JPanel(new GridBagLayout());
        this.tabbedPane.addTab("Modell", this.tabModel);

        GridBagConstraints gbcModel = new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(5, 5, 0, 5), 0, 0);

        ButtonGroup grpModel = new ButtonGroup();

        this.btnZ1013_01 = new JRadioButton("Z1013.01 (1 MHz, 16 KByte RAM)", false);
        this.btnZ1013_01.addActionListener(this);
        grpModel.add(this.btnZ1013_01);
        this.tabModel.add(this.btnZ1013_01, gbcModel);

        this.btnZ1013_12 = new JRadioButton("Z1013.12 (2 MHz, 1 KByte RAM)", false);
        this.btnZ1013_12.addActionListener(this);
        grpModel.add(this.btnZ1013_12);
        gbcModel.insets.top = 0;
        gbcModel.gridy++;
        this.tabModel.add(this.btnZ1013_12, gbcModel);

        this.btnZ1013_16 = new JRadioButton("Z1013.16 (2 MHz, 16 KByte RAM)", false);
        this.btnZ1013_16.addActionListener(this);
        grpModel.add(this.btnZ1013_16);
        gbcModel.gridy++;
        this.tabModel.add(this.btnZ1013_16, gbcModel);

        this.btnZ1013_64 = new JRadioButton("Z1013.64 (2 MHz, 64 KByte RAM)", false);
        this.btnZ1013_64.addActionListener(this);
        grpModel.add(this.btnZ1013_64);
        gbcModel.gridy++;
        this.tabModel.add(this.btnZ1013_64, gbcModel);

        this.btnZ1013_128 = new JRadioButton("Z1013.128 (2 MHz, 128 KByte RAM)", false);
        this.btnZ1013_128.addActionListener(this);
        grpModel.add(this.btnZ1013_128);
        gbcModel.gridy++;
        this.tabModel.add(this.btnZ1013_128, gbcModel);

        // Tab Monitorprogramm / Tastatur
        this.tabMon = new JPanel(new GridBagLayout());
        this.tabbedPane.addTab("Monitorprogramm / Tastatur", this.tabMon);

        GridBagConstraints gbcMon = new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(5, 5, 0, 5), 0, 0);

        ButtonGroup grpMon = new ButtonGroup();

        this.btnMon202 = new JRadioButton("Monitorprogramm 2.02 / Folienflachtastatur", true);
        this.btnMon202.addActionListener(this);
        grpMon.add(this.btnMon202);
        this.tabMon.add(this.btnMon202, gbcMon);

        this.btnMonA2 = new JRadioButton("Monitorprogramm A.2 / Alphatastatur", false);
        this.btnMonA2.addActionListener(this);
        grpMon.add(this.btnMonA2);
        gbcMon.insets.top = 0;
        gbcMon.gridy++;
        this.tabMon.add(this.btnMonA2, gbcMon);

        this.btnMonRB_K7659 = new JRadioButton("Brosig-Monitorprogramm 2.028 / Tastatur K7659", false);
        this.btnMonRB_K7659.addActionListener(this);
        grpMon.add(this.btnMonRB_K7659);
        gbcMon.gridy++;
        this.tabMon.add(this.btnMonRB_K7659, gbcMon);

        this.btnMonRB_S6009 = new JRadioButton("Brosig-Monitorprogramm 2.028 / Tastatur S6009", false);
        this.btnMonRB_S6009.addActionListener(this);
        grpMon.add(this.btnMonRB_S6009);
        gbcMon.gridy++;
        this.tabMon.add(this.btnMonRB_S6009, gbcMon);

        this.btnMonINCOM_K7669 = new JRadioButton("INCOM-Monitorprogramm 2.2 / Tastatur K7669", false);
        this.btnMonINCOM_K7669.addActionListener(this);
        grpMon.add(this.btnMonINCOM_K7669);
        gbcMon.gridy++;
        this.tabMon.add(this.btnMonINCOM_K7669, gbcMon);

        this.btnMonJM_1992 = new JRadioButton("M\u00FCller-Monitorprogramm 1992 / Folienflachtastatur", false);
        this.btnMonJM_1992.addActionListener(this);
        grpMon.add(this.btnMonJM_1992);
        gbcMon.gridy++;
        this.tabMon.add(this.btnMonJM_1992, gbcMon);

        this.btnBL4_K7659 = new JRadioButton("Boot Lader 4 / Tastatur K7659 (Boot-Diskette einlegen!)", false);
        this.btnBL4_K7659.addActionListener(this);
        grpMon.add(this.btnBL4_K7659);
        gbcMon.insets.bottom = 5;
        gbcMon.gridy++;
        this.tabMon.add(this.btnBL4_K7659, gbcMon);

        v128ExtensionsPanel = new jkcemu.emusys.z1013.v128.OptionPanel(this.settingsFrm, propPrefix);
        this.tabbedPane.addTab("Z1013.128", v128ExtensionsPanel);

        this.tabUserPort = new JPanel(new GridBagLayout());
        this.tabbedPane.addTab("Anwendertor", this.tabUserPort);

        GridBagConstraints gbcUserPort = new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0);

        this.tabUserPort.add(new JLabel("Am Anwendertor (User Port) emulierte Hardware:"), gbcUserPort);

        ButtonGroup grpPort = new ButtonGroup();

        this.btnPortNone = new JRadioButton("Keine angeschlossene Hardware emulieren", true);
        this.btnPortNone.addActionListener(this);
        grpPort.add(this.btnPortNone);
        gbcUserPort.insets.left = 50;
        gbcUserPort.insets.top = 0;
        gbcUserPort.insets.bottom = 0;
        gbcUserPort.gridy++;
        this.tabUserPort.add(this.btnPortNone, gbcUserPort);

        this.btnPortJoyJuTe_6_87 = new JRadioButton("1 Spielhebel nach JU+TE 06/1987", false);
        this.btnPortJoyJuTe_6_87.addActionListener(this);
        grpPort.add(this.btnPortJoyJuTe_6_87);
        gbcUserPort.gridy++;
        this.tabUserPort.add(this.btnPortJoyJuTe_6_87, gbcUserPort);

        this.btnPortJoyPractic_4_87 = new JRadioButton("2 Spielhebel nach practic 04/1987", false);
        this.btnPortJoyPractic_4_87.addActionListener(this);
        grpPort.add(this.btnPortJoyPractic_4_87);
        gbcUserPort.gridy++;
        this.tabUserPort.add(this.btnPortJoyPractic_4_87, gbcUserPort);

        this.btnPortJoyPractic_1_88 = new JRadioButton("2 Spielhebel nach practic 01/1988", false);
        this.btnPortJoyPractic_1_88.addActionListener(this);
        grpPort.add(this.btnPortJoyPractic_1_88);
        gbcUserPort.gridy++;
        this.tabUserPort.add(this.btnPortJoyPractic_1_88, gbcUserPort);

        this.btnPortCentr7Practic_2_89 = new JRadioButton(
                "Drucker an 7-Bit-Centronics-Anschluss" + " nach practic 02/1989", false);
        this.btnPortCentr7Practic_2_89.addActionListener(this);
        grpPort.add(this.btnPortCentr7Practic_2_89);
        gbcUserPort.gridy++;
        this.tabUserPort.add(this.btnPortCentr7Practic_2_89, gbcUserPort);

        this.btnPortCentr8FA_10_90 = new JRadioButton("Drucker an 8-Bit-Centronics-Anschluss nach FA 10/1990", false);
        this.btnPortCentr8FA_10_90.addActionListener(this);
        grpPort.add(this.btnPortCentr8FA_10_90);
        gbcUserPort.insets.bottom = 5;
        gbcUserPort.gridy++;
        this.tabUserPort.add(this.btnPortCentr8FA_10_90, gbcUserPort);

        // Tab RAM-Floppies
        this.tabRF = new RAMFloppiesSettingsFld(settingsFrm, propPrefix,
                "RAM-Floppy nach MP 3/88 (256 KByte) an E/A-Adressen 98h-9Fh", RAMFloppy.RFType.MP_3_1988,
                "RAM-Floppy nach MP 3/88 (256 KByte) an E/A-Adressen 58h-5Fh", RAMFloppy.RFType.MP_3_1988);
        this.tabbedPane.addTab("RAM-Floppies", this.tabRF);

        // Tab ROM-Module
        this.tabModROM = new JPanel(new GridBagLayout());
        this.tabbedPane.addTab("ROM-Module", this.tabModROM);

        GridBagConstraints gbcModROM = new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0);

        this.btnModBasic = new JCheckBox("KC-BASIC-Modul (C000h-EBFFh)");
        gbcModROM.gridy++;
        this.tabModROM.add(this.btnModBasic, gbcModROM);

        this.fldAltBasic = new ROMFileSettingsFld(settingsFrm, propPrefix + Z1013.PROP_ROMBASIC_PREFIX,
                "Alternativer Inhalt des KC-BASIC-Moduls:");
        gbcModROM.fill = GridBagConstraints.HORIZONTAL;
        gbcModROM.weightx = 1.0;
        gbcModROM.insets.left = 50;
        gbcModROM.insets.top = 0;
        gbcModROM.gridy++;
        this.tabModROM.add(this.fldAltBasic, gbcModROM);

        this.btnModMegaROM = new JCheckBox("Mega-ROM-Modul (256 x C000h-E7FFh)");
        gbcModROM.fill = GridBagConstraints.NONE;
        gbcModROM.weightx = 0.0;
        gbcModROM.insets.top = 5;
        gbcModROM.insets.left = 5;
        gbcModROM.gridy++;
        this.tabModROM.add(this.btnModMegaROM, gbcModROM);

        this.fldMegaROM = new ROMFileSettingsFld(settingsFrm, propPrefix + Z1013.PROP_ROMMEGA_PREFIX,
                "Inhalt des Mega-ROM-Moduls:");
        gbcModROM.fill = GridBagConstraints.HORIZONTAL;
        gbcModROM.weightx = 1.0;
        gbcModROM.insets.top = 0;
        gbcModROM.insets.left = 50;
        gbcModROM.gridy++;
        this.tabModROM.add(this.fldMegaROM, gbcModROM);

        updROMModFieldsEnabled();
        this.btnModBasic.addActionListener(this);
        this.btnModMegaROM.addActionListener(this);

        // GIDE
        this.tabGIDE = new GIDESettingsFld(settingsFrm, propPrefix);
        this.tabbedPane.addTab("GIDE", this.tabGIDE);

        // Tab Erweiterungen
        this.tabExt = new JPanel(new GridBagLayout());
        this.tabbedPane.addTab("Erweiterungen", this.tabExt);

        GridBagConstraints gbcExt = new GridBagConstraints(0, 0, GridBagConstraints.REMAINDER, 1, 1.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 0, 5), 0, 0);

        this.btnFloppyDisk = new JCheckBox("Floppy-Disk-Modul", false);
        this.btnFloppyDisk.addActionListener(this);
        this.tabExt.add(this.btnFloppyDisk, gbcExt);

        this.btnKCNet = new JCheckBox("KCNet-kompatible Netzwerkkarte", false);
        gbcExt.insets.top = 0;
        gbcExt.gridy++;
        this.btnKCNet.addActionListener(this);
        this.tabExt.add(this.btnKCNet, gbcExt);

        this.btnVDIP = new JCheckBox("USB-Anschluss (Vinculum VDIP Modul)", false);
        this.btnVDIP.addActionListener(this);
        gbcExt.gridy++;
        this.tabExt.add(this.btnVDIP, gbcExt);

        this.btnRTC = new JCheckBox("Echtzeituhr", false);
        this.btnRTC.addActionListener(this);
        gbcExt.gridy++;
        this.tabExt.add(this.btnRTC, gbcExt);

        this.btnGraphic = new JCheckBox("Vollgrafikerweiterung nach KRT 11 und FA 7/91", false);
        this.btnGraphic.addActionListener(this);
        gbcExt.gridy++;
        this.tabExt.add(this.btnGraphic, gbcExt);

        this.btnGraphCCJ = new JCheckBox("Grafikkarte des CC Jena (80x25 Zeichen)", false);
        this.btnGraphCCJ.addActionListener(this);
        gbcExt.insets.bottom = 5;
        gbcExt.gridy++;
        this.tabExt.add(this.btnGraphCCJ, gbcExt);

        this.fldAltGCCJFont = new ROMFileSettingsFld(settingsFrm, this.propPrefix + Z1013.PROP_GCCJ_FONT_FILE,
                "Alternativer Zeichensatz:");
        gbcExt.insets.left = 50;
        gbcExt.gridy++;
        this.tabExt.add(this.fldAltGCCJFont, gbcExt);
        updAltGCCJFontFieldsEnabled();

        // Tab Sonstiges
        this.tabEtc = new JPanel(new GridBagLayout());
        this.tabbedPane.addTab("Sonstiges", this.tabEtc);

        GridBagConstraints gbcEtc = new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(5, 5, 0, 5), 0, 0);

        this.btnCatchPrintCalls = new JCheckBox("Sprungverteileraufrufe f\u00FCr Druckerausgaben abfangen", true);
        this.btnCatchPrintCalls.addActionListener(this);
        this.tabEtc.add(this.btnCatchPrintCalls, gbcEtc);

        this.btnCatchJoyCalls = new JCheckBox("Sprungverteileraufrufe f\u00FCr Joystick-Abfragen abfangen", true);
        this.btnCatchJoyCalls.addActionListener(this);
        gbcEtc.insets.top = 0;
        gbcEtc.gridy++;
        this.tabEtc.add(this.btnCatchJoyCalls, gbcEtc);

        this.btnPasteFast = new JCheckBox("Einf\u00FCgen von Text durch Abfangen des Systemaufrufs", true);
        this.btnPasteFast.addActionListener(this);
        gbcEtc.insets.bottom = 5;
        gbcEtc.gridy++;
        this.tabEtc.add(this.btnPasteFast, gbcEtc);

        gbcEtc.fill = GridBagConstraints.HORIZONTAL;
        gbcEtc.weightx = 1.0;
        gbcEtc.insets.top = 10;
        gbcEtc.insets.bottom = 10;
        gbcEtc.gridy++;
        this.tabEtc.add(new JSeparator(), gbcEtc);

        this.fldAltOS = new ROMFileSettingsFld(settingsFrm, propPrefix + Z1013.PROP_OS_PREFIX,
                "Alternatives Monitorprogramm (F000h-FFFFh):");
        gbcEtc.insets.top = 5;
        gbcEtc.insets.bottom = 5;
        gbcEtc.gridy++;
        this.tabEtc.add(this.fldAltOS, gbcEtc);

        this.fldAltFont = new ROMFileSettingsFld(settingsFrm, propPrefix + Z1013.PROP_FONT_PREFIX,
                "Alternativer Zeichensatz:");
        gbcEtc.gridy++;
        this.tabEtc.add(this.fldAltFont, gbcEtc);

        // Tab AutoLoad
        this.tabAutoLoad = new AutoLoadSettingsFld(settingsFrm, propPrefix, Z1013.DEFAULT_PROMPT_AFTER_RESET_MILLIS_MAX,
                true);
        this.tabbedPane.addTab("AutoLoad", this.tabAutoLoad);

        // Tab AutoInput
        this.tabAutoInput = new AutoInputSettingsFld(settingsFrm, propPrefix, Z1013.DEFAULT_SWAP_KEY_CHAR_CASE,
                Z1013.FUNCTION_KEY_COUNT, Z1013.DEFAULT_PROMPT_AFTER_RESET_MILLIS_MAX);
        this.tabbedPane.addTab("AutoInput", this.tabAutoInput);
    }

    public String getModelSysName() {
        String rv = Z1013.SYSNAME_Z1013_64;
        if (this.btnZ1013_01.isSelected()) {
            rv = Z1013.SYSNAME_Z1013_01;
        } else if (this.btnZ1013_12.isSelected()) {
            rv = Z1013.SYSNAME_Z1013_12;
        } else if (this.btnZ1013_16.isSelected()) {
            rv = Z1013.SYSNAME_Z1013_16;
        } else if (this.btnZ1013_128.isSelected()) {
            rv = Z1013.SYSNAME_Z1013_128;
        }

        return rv;
    }

    /* --- ueberschriebene Methoden --- */

    @Override
    public void applyInput(Properties props, boolean selected) throws UserCancelException, UserInputException {
        Component tab = null;
        String text = null;
        try {

            // Tab Monitorprogramm / Tastatur
            tab = this.tabModel;
            text = Z1013.VALUE_MON_202;
            if (this.btnMonA2.isSelected()) {
                text = Z1013.VALUE_MON_A2;
            } else if (this.btnMonRB_K7659.isSelected()) {
                text = Z1013.VALUE_MON_RB_K7659;
            } else if (this.btnMonRB_S6009.isSelected()) {
                text = Z1013.VALUE_MON_RB_S6009;
            } else if (this.btnMonINCOM_K7669.isSelected()) {
                text = Z1013.VALUE_MON_INCOM_K7669;
            } else if (this.btnMonJM_1992.isSelected()) {
                text = Z1013.VALUE_MON_JM_1992;
            } else if (this.btnBL4_K7659.isSelected()) {
                text = Z1013.VALUE_MON_BL4_K7659;
            }
            EmuUtil.setProperty(props, Z1013.PROP_PREFIX + Z1013.PROP_MONITOR, text);

            // Tab Anwendertor
            tab = this.tabUserPort;
            text = "none";
            if (this.btnPortJoyJuTe_6_87.isSelected()) {
                text = Z1013.VALUE_JOY_JUTE0687;
            } else if (this.btnPortJoyPractic_4_87.isSelected()) {
                text = Z1013.VALUE_JOY_PRAC0487;
            } else if (this.btnPortJoyPractic_1_88.isSelected()) {
                text = Z1013.VALUE_JOY_PRAC0188;
            } else if (this.btnPortCentr7Practic_2_89.isSelected()) {
                text = Z1013.VALUE_CEN7_PRAC0289;
            } else if (this.btnPortCentr8FA_10_90.isSelected()) {
                text = Z1013.VALUE_CEN8_FA1090;
            }
            EmuUtil.setProperty(props, this.propPrefix + Z1013.PROP_USERPORT, text);

            // Tab RAM-Floppies
            tab = this.tabRF;
            this.tabRF.applyInput(props, selected);

            // Tab ROM-Module
            tab = this.tabModROM;
            EmuUtil.setProperty(props, this.propPrefix + Z1013.PROP_ROMBASIC_ENABLED, this.btnModBasic.isSelected());
            this.fldAltBasic.applyInput(props, selected);

            boolean stateMega = this.btnModMegaROM.isSelected();
            if (stateMega && (this.fldMegaROM.getFile() == null)) {
                throw new UserInputException("Datei f\u00FCr Mega-ROM-Modul nicht ausgew\u00E4hlt");
            }
            EmuUtil.setProperty(props, this.propPrefix + Z1013.PROP_ROMMEGA_ENABLED, stateMega);
            this.fldMegaROM.applyInput(props, selected);

            // Tab GIDE
            tab = this.tabGIDE;
            this.tabGIDE.applyInput(props, selected);

            // Tab Erweiterungen
            tab = this.tabExt;
            EmuUtil.setProperty(props, this.propPrefix + Z1013.PROP_FDC_ENABLED, this.btnFloppyDisk.isSelected());
            EmuUtil.setProperty(props, this.propPrefix + Z1013.PROP_RTC_ENABLED, this.btnRTC.isSelected());
            EmuUtil.setProperty(props, this.propPrefix + Z1013.PROP_KCNET_ENABLED, this.btnKCNet.isSelected());
            EmuUtil.setProperty(props, this.propPrefix + Z1013.PROP_VDIP_ENABLED, this.btnVDIP.isSelected());
            EmuUtil.setProperty(props, this.propPrefix + Z1013.PROP_GRAPHIC_ENABLED, this.btnGraphic.isSelected());
            EmuUtil.setProperty(props, this.propPrefix + Z1013.PROP_GCCJ_ENABLED, this.btnGraphCCJ.isSelected());
            this.fldAltGCCJFont.applyInput(props, selected);

            // Tab Sonstiges
            tab = this.tabEtc;
            EmuUtil.setProperty(props, this.propPrefix + Z1013.PROP_CATCH_PRINT_CALLS,
                    this.btnCatchPrintCalls.isSelected());
            EmuUtil.setProperty(props, this.propPrefix + Z1013.PROP_CATCH_JOY_CALLS,
                    this.btnCatchJoyCalls.isSelected());
            EmuUtil.setProperty(props, this.propPrefix + Z1013.PROP_PASTE_FAST, this.btnPasteFast.isSelected());
            this.fldAltOS.applyInput(props, selected);
            this.fldAltFont.applyInput(props, selected);

            // Tab AutoLoad
            tab = this.tabAutoLoad;
            this.tabAutoLoad.applyInput(props, selected);

            // Tab AutoInput
            tab = this.tabAutoInput;
            this.tabAutoInput.applyInput(props, selected);

            // ggf. Warnung ausgeben
            if (selected && this.btnMonA2.isSelected() && this.btnModBasic.isSelected()
                    && (this.fldAltBasic.getFile() == null)) {
                if (!BaseDlg.showYesNoWarningDlg(this, "Das im ROM-Modul enthaltene KC-BASIC l\u00E4uft nicht"
                        + " mit dem Monitorprogramm A.2 zusammen.\n" + "Sie sollten deshalb entweder ein anderes"
                        + " Monitorprogramm ausw\u00E4hlen" + " oder f\u00FCr das ROM-Modul\n"
                        + "einen alternativen, an das Monitorprogramm" + " angepassten Inhalt einbinden.\n" + "\n"
                        + "M\u00F6chten Sie trotzdem die Einstellungen" + " \u00FCbernehmen?", "Konflikt")) {
                    throw new UserCancelException();
                }
            }
            this.v128ExtensionsPanel.settings.applySettings(props);
        } catch (UserInputException ex) {
            if (tab != null) {
                this.tabbedPane.setSelectedComponent(tab);
            }
            throw ex;
        }
    }

    @Override
    protected boolean doAction(EventObject e) {
        this.settingsFrm.setWaitCursor(true);

        boolean rv = false;
        Object src = e.getSource();
        if (src != null) {
            if (src == this.btnModBasic) {
                rv = true;
                if (this.btnModBasic.isSelected() && this.btnModMegaROM.isSelected()) {
                    this.btnModMegaROM.setSelected(false);
                }
                updROMModFieldsEnabled();
                fireDataChanged();
            } else if (src == this.btnModMegaROM) {
                rv = true;
                if (this.btnModBasic.isSelected() && this.btnModMegaROM.isSelected()) {
                    this.btnModBasic.setSelected(false);
                }
                updROMModFieldsEnabled();
                fireDataChanged();
            } else if (src == this.btnGraphCCJ) {
                rv = true;
                updAltGCCJFontFieldsEnabled();
                fireDataChanged();
            } else if (src instanceof AbstractButton) {
                rv = true;
                fireDataChanged();
            }
        }
        if (!rv) {
            rv = this.tabGIDE.doAction(e);
        }
        if (!rv) {
            rv = this.tabAutoLoad.doAction(e);
        }
        if (!rv) {
            rv = this.tabAutoInput.doAction(e);
        }
        this.settingsFrm.setWaitCursor(false);
        return rv;
    }

    @Override
    public void lookAndFeelChanged() {
        this.fldAltOS.lookAndFeelChanged();
        this.fldAltFont.lookAndFeelChanged();
        this.fldAltGCCJFont.lookAndFeelChanged();
        this.fldAltBasic.lookAndFeelChanged();
        this.fldMegaROM.lookAndFeelChanged();
        this.tabGIDE.lookAndFeelChanged();
        this.tabAutoLoad.lookAndFeelChanged();
        this.tabAutoInput.lookAndFeelChanged();
    }

    @Override
    public void updFields(Properties props) {
        switch (EmuUtil.getProperty(props, EmuThread.PROP_SYSNAME)) {
        case Z1013.SYSNAME_Z1013_01:
            this.btnZ1013_01.setSelected(true);
            break;
        case Z1013.SYSNAME_Z1013_12:
            this.btnZ1013_12.setSelected(true);
            break;
        case Z1013.SYSNAME_Z1013_16:
            this.btnZ1013_16.setSelected(true);
            break;
        case Z1013.SYSNAME_Z1013_128:
            this.btnZ1013_128.setSelected(true);
            break;
        default:
            this.btnZ1013_64.setSelected(true);
        }

        String mon = EmuUtil.getProperty(props, Z1013.PROP_PREFIX + Z1013.PROP_MONITOR);
        if (mon.equals(Z1013.VALUE_MON_A2)) {
            this.btnMonA2.setSelected(true);
        } else if (mon.equals(Z1013.VALUE_MON_RB_K7659)) {
            this.btnMonRB_K7659.setSelected(true);
        } else if (mon.equals(Z1013.VALUE_MON_RB_S6009)) {
            this.btnMonRB_S6009.setSelected(true);
        } else if (mon.equals(Z1013.VALUE_MON_INCOM_K7669)) {
            this.btnMonINCOM_K7669.setSelected(true);
        } else if (mon.equals(Z1013.VALUE_MON_JM_1992)) {
            this.btnMonJM_1992.setSelected(true);
        } else if (mon.equals(Z1013.VALUE_MON_BL4_K7659)) {
            this.btnBL4_K7659.setSelected(true);
        } else {
            this.btnMon202.setSelected(true);
        }

        String port = EmuUtil.getProperty(props, this.propPrefix + Z1013.PROP_USERPORT);
        if (port.equals(Z1013.VALUE_JOY_JUTE0687)) {
            this.btnPortJoyJuTe_6_87.setSelected(true);
        } else if (port.equals(Z1013.VALUE_JOY_PRAC0487)) {
            this.btnPortJoyPractic_4_87.setSelected(true);
        } else if (port.equals(Z1013.VALUE_JOY_PRAC0188)) {
            this.btnPortJoyPractic_1_88.setSelected(true);
        } else if (port.equals(Z1013.VALUE_CEN7_PRAC0289)) {
            this.btnPortCentr7Practic_2_89.setSelected(true);
        } else if (port.equals(Z1013.VALUE_CEN8_FA1090)) {
            this.btnPortCentr8FA_10_90.setSelected(true);
        } else {
            this.btnPortNone.setSelected(true);
        }

        this.tabRF.updFields(props);
        this.tabGIDE.updFields(props);

        this.btnModBasic
                .setSelected(EmuUtil.getBooleanProperty(props, this.propPrefix + Z1013.PROP_ROMBASIC_ENABLED, false));
        this.btnModMegaROM.setSelected(!this.btnModBasic.isSelected()
                && EmuUtil.getBooleanProperty(props, this.propPrefix + Z1013.PROP_ROMMEGA_ENABLED, false));
        updROMModFieldsEnabled();
        this.fldAltBasic.updFields(props);
        this.fldMegaROM.updFields(props);

        this.btnFloppyDisk
                .setSelected(EmuUtil.getBooleanProperty(props, this.propPrefix + Z1013.PROP_FDC_ENABLED, false));
        this.btnRTC.setSelected(EmuUtil.getBooleanProperty(props, this.propPrefix + Z1013.PROP_RTC_ENABLED, false));
        this.btnKCNet.setSelected(EmuUtil.getBooleanProperty(props, this.propPrefix + Z1013.PROP_KCNET_ENABLED, false));
        this.btnVDIP.setSelected(EmuUtil.getBooleanProperty(props, this.propPrefix + Z1013.PROP_VDIP_ENABLED, false));
        this.btnGraphic
                .setSelected(EmuUtil.getBooleanProperty(props, this.propPrefix + Z1013.PROP_GRAPHIC_ENABLED, false));
        this.btnGraphCCJ
                .setSelected(EmuUtil.getBooleanProperty(props, this.propPrefix + Z1013.PROP_GCCJ_ENABLED, false));
        updAltGCCJFontFieldsEnabled();
        this.fldAltGCCJFont.updFields(props);

        this.btnCatchPrintCalls
                .setSelected(EmuUtil.getBooleanProperty(props, this.propPrefix + Z1013.PROP_CATCH_PRINT_CALLS, true));
        this.btnCatchJoyCalls
                .setSelected(EmuUtil.getBooleanProperty(props, this.propPrefix + Z1013.PROP_CATCH_JOY_CALLS, true));
        this.btnPasteFast.setSelected(EmuUtil.getBooleanProperty(props, this.propPrefix + Z1013.PROP_PASTE_FAST, true));
        this.fldAltOS.updFields(props);
        this.fldAltFont.updFields(props);

        this.tabAutoLoad.updFields(props);
        this.tabAutoInput.updFields(props);

        this.v128ExtensionsPanel.settings.updFields(props);
    }

    /* --- private Methoden --- */

    private void updAltGCCJFontFieldsEnabled() {
        this.fldAltGCCJFont.setEnabled(btnGraphCCJ.isSelected());
    }

    private void updROMModFieldsEnabled() {
        this.fldAltBasic.setEnabled(this.btnModBasic.isSelected());
        this.fldMegaROM.setEnabled(this.btnModMegaROM.isSelected());
    }
}
