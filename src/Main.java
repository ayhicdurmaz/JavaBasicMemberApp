import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.List;
import java.util.ArrayList;

class UyeKayitUI extends JFrame implements ActionListener {
    
    private JLabel labelAdSoyad, labelTelNo, labelEmail, labelCinsiyet;
    private JTextField textFieldAdSoyad, textFieldTelNo, textFieldEmail;
    private JRadioButton rButtonE, rButtonK, rButtonD;
    private JButton buttonKayit;
    private ButtonGroup buttonGroup;

    public UyeKayitUI(){

        setTitle("Uye Kayit");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

        FileService fileService = new FileService();

        JPanel panelAdSoyad = new JPanel(new FlowLayout(FlowLayout.LEFT, 25, 25));

            labelAdSoyad = new JLabel("Ad Soyad : ");
            labelAdSoyad.setPreferredSize(new Dimension(150, 10));
            textFieldAdSoyad = new JTextField(10);
            
            panelAdSoyad.add(labelAdSoyad);
            panelAdSoyad.add(textFieldAdSoyad);

        JPanel panelTelNo = new JPanel(new FlowLayout(FlowLayout.LEFT, 25, 25));

            labelTelNo = new JLabel("Telefon Numarası : ");
            labelTelNo.setPreferredSize(new Dimension(150, 10));
            textFieldTelNo = new JTextField(10);
            
            panelTelNo.add(labelTelNo);
            panelTelNo.add(textFieldTelNo);

        JPanel panelEmail = new JPanel(new FlowLayout(FlowLayout.LEFT, 25, 25));

            labelEmail = new JLabel("Email : ");
            labelEmail.setPreferredSize(new Dimension(150, 10));
            textFieldEmail = new JTextField(10);

            panelEmail.add(labelEmail);
            panelEmail.add(textFieldEmail);

        JPanel panelCinsiyet = new JPanel(new FlowLayout(FlowLayout.LEFT, 25, 25));

            labelCinsiyet = new JLabel("Cinsiyet : ");
            rButtonE = new JRadioButton("Erkek");
            rButtonK = new JRadioButton("Kadın");
            rButtonD = new JRadioButton("Belirtmek İstemiyorum");

            buttonGroup = new ButtonGroup();

                buttonGroup.add(rButtonE);
                buttonGroup.add(rButtonK);
                buttonGroup.add(rButtonD);

            panelCinsiyet.add(labelCinsiyet);
            panelCinsiyet.add(rButtonE);
            panelCinsiyet.add(rButtonK);
            panelCinsiyet.add(rButtonD);


        JPanel panelKayit = new JPanel(new FlowLayout());

            panelKayit.setPreferredSize(new Dimension(500, 50));
            buttonKayit = new JButton("Kayıt");
            buttonKayit.setPreferredSize(new Dimension(500, 50));
            buttonKayit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(controlInfos()){
                        String[] pastMembers = fileService.fileToArray();
                        String[] members = fileService.addMemberToList(pastMembers, getInfos().getLine(), pastMembers.length);
                        fileService.arrayToFile(members);
                    }
                }
            });
            panelKayit.add(buttonKayit);


        add(panelAdSoyad);
        add(panelTelNo);
        add(panelEmail);
        add(panelCinsiyet);
        add(buttonKayit);

        setVisible(true);

    }

    private boolean controlInfos(){
    
        boolean control = false;
        
        if(!textFieldAdSoyad.getText().isEmpty()) { control = true; } else { control = false; }
        if(!textFieldTelNo.getText().isEmpty())   { control = true; } else { control = false; }
        if(!textFieldEmail.getText().isEmpty())   { control = true; } else { control = false; }
        if(buttonGroup.getSelection() != null)    { control = true; } else { control = false; }

        return control;
    
    }

    private Uye getInfos(){

        Uye uye = new Uye();

        uye.setAdSoyad(textFieldAdSoyad.getText());
        uye.setTelNo(textFieldTelNo.getText());
        uye.setEmail(textFieldEmail.getText());
        if(rButtonE.isSelected()){
            uye.setCinsiyet("ERKEK");
        }
        if(rButtonK.isSelected()){
            uye.setCinsiyet("KADIN");
        }
        if(rButtonD.isSelected()){
            uye.setCinsiyet("BELİRTİLMEDİ");
        }

        return uye;

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }

}

class Uye{
    private String adSoyad;
    private String telNo;
    private String email;
    private String cinsiyet;

    public String getAdSoyad() { return adSoyad; }
    public void setAdSoyad(String adSoyad) { this.adSoyad = adSoyad; }
    public String getTelNo() { return telNo; }
    public void setTelNo(String telNo) { this.telNo = telNo; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getCinsiyet() { return cinsiyet; }
    public void setCinsiyet(String cinsiyet) { this.cinsiyet = cinsiyet; }
    public String getLine() { return (adSoyad + '\t' + telNo + '\t' + email + '\t' + cinsiyet); } 

}

class FileService {
    
    private String filePath = "uye.txt";
    public String getFilePath() { return filePath; }
    

    public FileService() {
        try{
            File f = new File(filePath);
            f.createNewFile();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    public void setFilePath(String filePath) {
        try {
            File f = new File(filePath);
            f.getAbsolutePath();
            filePath = f.getAbsolutePath();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    public void arrayToFile(String[] _satirlar){
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath));
            for (String satir: _satirlar) {
                bufferedWriter.append(satir + '\n');
            }
            bufferedWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String[] fileToArray(){
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
            List<String> dosya = new ArrayList<String>();
            String satir = bufferedReader.readLine();
            while(satir != null) {
                dosya.add(satir);
                satir = bufferedReader.readLine();
            }
            bufferedReader.close();
            return dosya.toArray(new String[0]);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String[] addMemberToList(String[] array, String data, int index) {
        String[] new_arr = new String[array.length + 1];

        for (int i = array.length - 1; i >= index; i--) {
            new_arr[i + 1] = array[i];
        }

        for (int i = 0; i < index; i++) {
            new_arr[i] = array[i];
        }
        new_arr[index] = data;
        return new_arr;
    }
}

public class Main {

    public static void main(String[] args) throws Exception {
        new UyeKayitUI();
        System.out.println("Hello, World!");
    }
}
