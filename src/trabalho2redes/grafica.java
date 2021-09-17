/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalho2redes;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

/**
 *
 * @author dumed
 */
public class grafica extends JFrame {

    private final JPanel info;
    private final JPanel info2;
    private final JPanel info3;
    private final JPanel noticias;

    private final JLabel titulo;
    private final JLabel data;
    private final JLabel temperaturaMax;
    private final JLabel temperaturaMin;
    private final JLabel noticia1;
    private final JLabel noticia2;
    private final JLabel noticia3;

    public grafica() {
        super("Trabalho 2 - Redes");

        super.setLayout(new GridLayout(2, 0));
        super.setVisible(true);
        super.setBackground(Color.LIGHT_GRAY);
        super.setSize(780, 520);
        super.setLocationRelativeTo(null);
        //super.setResizable(false);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Font f = new Font("Sans Serif", Font.BOLD, 20);
        Font g = new Font("Extra Bold", Font.BOLD, 30);
        Font h = new Font("Times New Roman", Font.PLAIN, 20 );

        titulo = new JLabel("Jaguarão");
        titulo.setFont(g);

        temperaturaMin = new JLabel("");
        temperaturaMin.setFont(h);

        temperaturaMax = new JLabel("");
        temperaturaMax.setFont(h);

        info2 = new JPanel();
        info2.setLayout(new GridLayout(3, 1));
        info2.setBackground(Color.LIGHT_GRAY);

        info2.add(this.titulo);
        info2.add(this.temperaturaMin);
        info2.add(this.temperaturaMax);

        data = new JLabel();
        data.setFont(g);
        //data.setAlignmentX(Label.BOTTOM_ALIGNMENT);

        info3 = new JPanel();
        info3.setLayout(new GridLayout(3, 1));
        info3.setBackground(Color.LIGHT_GRAY);
        
        info3.add(this.data); 

        info = new JPanel();
        info.setLayout(new GridLayout(1, 2));

        info.add(this.info2);
        info.add(this.info3);

        Border blackLine = BorderFactory.createLineBorder(Color.black);
        TitledBorder titledInfo;
        titledInfo = BorderFactory.createTitledBorder(blackLine, "Tempo Hoje");
        titledInfo.setTitleColor(Color.RED);
        info.setBorder(titledInfo);

        noticia1 = new JLabel();
        noticia2 = new JLabel();
        noticia3 = new JLabel();

        noticia1.setBorder(blackLine);
        noticia2.setBorder(blackLine);
        noticia3.setBorder(blackLine);

        noticia1.setFont(h);
        noticia2.setFont(h);
        noticia3.setFont(h);

        noticias = new JPanel();
        noticias.setLayout(new GridLayout(3, 1));

        noticias.add(this.noticia1);
        noticias.add(this.noticia2);
        noticias.add(this.noticia3);

        TitledBorder titledNoticias;
        titledNoticias = BorderFactory.createTitledBorder(blackLine, "Notícias do dia");
        titledNoticias.setTitleColor(Color.RED);
        noticias.setBorder(titledNoticias);

        add(info);
        add(noticias);
        
        openConnetionData();
        openConnetionTemperatura();
        openConnetionNoticia1();
        openConnetionNoticia2();
        openConnetionNoticia3();
    }

    public final void openConnetionData() {
        try {
            URL url = new URL("https://www.calendarr.com/brasil/");

            int responseCode;

            HttpURLConnection conData = (HttpURLConnection) url.openConnection();
            conData.setRequestMethod("GET");
            conData.setRequestProperty("User-Agent", "Mozilla/5.0");
            conData.setConnectTimeout(5000); //tempo para estabelecer a conexao
            conData.setReadTimeout(5000);
            //  conData.setInstanceFollowRedirects(false); //sem redirecionamento

            responseCode = conData.getResponseCode();

            if (responseCode == 200) {
                // System.out.println("Código de resposta: " + responseCode);
                //deu certo
                BufferedReader in = new BufferedReader(new InputStreamReader(conData.getInputStream()));
                String inputLine = null;
                while ((inputLine = in.readLine()) != null) {
                    pegaData(inputLine);
                    //System.out.println(inputLine);
                }
                //fechando os canais de comunicacao
                in.close();
            } else {
                //301 --> movido permanentemente
                if (responseCode > 300 && responseCode <= 302) {
                    System.out.println("Movido permanentemente. Redirecionando...");
                    String location = conData.getHeaderField("Location");  //pega um campo do cabeçalho
                    System.out.println("Foi movido");
                    //openConnection(location); //abre a conexao nessa nova localização
                }

            }
        } catch (MalformedURLException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }

    public void pegaData(String linha) {
        Pattern pattern = Pattern.compile("<\\s*b\\>(.*)\\<\\/b\\>");
        Matcher matcher = pattern.matcher(linha);

        if (matcher.find()) {
            data.setText(matcher.group(1));
            return;
        }

    }

    public final void openConnetionTemperatura() {
        try {
            URL url = new URL("https://www.climatempo.com.br/previsao-do-tempo/cidade/1397/jaguarao-rs");

            int responseCode;

            HttpURLConnection conTemp = (HttpURLConnection) url.openConnection();
            conTemp.setRequestMethod("GET");
            conTemp.setRequestProperty("User-Agent", "Mozilla/5.0");
            conTemp.setConnectTimeout(5000); //tempo para estabelecer a conexao
            conTemp.setReadTimeout(5000);
            //  conData.setInstanceFollowRedirects(false); //sem redirecionamento

            responseCode = conTemp.getResponseCode();
            //System.out.println("Código de resposta: " + responseCode);

            if (responseCode == 200) {

                //deu certo
                BufferedReader in = new BufferedReader(new InputStreamReader(conTemp.getInputStream()));
                String inputLine = null;
                while ((inputLine = in.readLine()) != null) {
                    pegaTemperatura(inputLine);
                    //System.out.println(inputLine);
                }
                //fechando os canais de comunicacao
                in.close();
            } else {
                //301 --> movido permanentemente
                if (responseCode > 300 && responseCode <= 302) {
                    System.out.println("Movido permanentemente. Redirecionando...");
                    String location = conTemp.getHeaderField("Location");  //pega um campo do cabeçalho
                    System.out.println("Foi movido");
                }

            }
        } catch (MalformedURLException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }

    public void pegaTemperatura(String linha) {

        Pattern pattern = Pattern.compile("\\s*id\\=\\\"min-temp-1\\\"\\>(.*)\\<\\/\\s*span\\>");
        Matcher matcher = pattern.matcher(linha);

        if (matcher.find()) {
            temperaturaMin.setText("Mínima hoje: " + matcher.group(1));
            return;
        }

        pattern = Pattern.compile("\\s*id\\=\\\"max-temp-1\\\"\\>(.*)\\<\\/\\s*span\\>");
        matcher = pattern.matcher(linha);

        if (matcher.find()) {
            temperaturaMax.setText("Máxima hoje: " + matcher.group(1));
            return;
        }

    }

    public final void openConnetionNoticia1() {
        try {
            URL url = new URL("https://www.folha.uol.com.br/");

            int responseCode;

            HttpURLConnection conNoticia1 = (HttpURLConnection) url.openConnection();
            conNoticia1.setRequestMethod("GET");
            conNoticia1.setRequestProperty("User-Agent", "Mozilla/5.0");
            conNoticia1.setConnectTimeout(5000); //tempo para estabelecer a conexao
            conNoticia1.setReadTimeout(5000);
            //  conData.setInstanceFollowRedirects(false); //sem redirecionamento

            responseCode = conNoticia1.getResponseCode();
            System.out.println("Código de resposta: " + responseCode);

            if (responseCode == 200) {
                //System.out.println("Código de resposta: " + responseCode);
                //deu certo
                BufferedReader in = new BufferedReader(new InputStreamReader(conNoticia1.getInputStream()));
                String inputLine = null;
                while ((inputLine = in.readLine()) != null) {
                    pegaNoticia1(inputLine);
                }
                //fechando os canais de comunicacao
                in.close();
            } else {
                if (responseCode > 300 && responseCode <= 302) {
                    System.out.println("");
                    System.out.println("foi movido");
                    //openConnectionNoticia1(location); //abre a conexao nessa nova localização
                }

            }
        } catch (MalformedURLException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public final void pegaNoticia1(String linha) {
        Pattern pattern = Pattern.compile("\\s*\\<\\s*h2\\s*class\\=\\\"c-main-headline__title\\\"\\>(.*)\\<\\/h2\\>");
        Matcher matcher = pattern.matcher(linha);

        if (matcher.find()) {
            noticia1.setText("<html><br style=\"width:200px\">"+matcher.group(1)+ " - Fonte: A Folha" + "</br></html>");
            return;
        }
    }

    public final void openConnetionNoticia2() {
        try {
            URL url = new URL("https://www1.folha.uol.com.br/cotidiano/coronavirus/");

            int responseCode;

            HttpURLConnection conNoticia2 = (HttpURLConnection) url.openConnection();
            conNoticia2.setRequestMethod("GET");
            conNoticia2.setRequestProperty("User-Agent", "Mozilla/5.0");
            conNoticia2.setConnectTimeout(5000); //tempo para estabelecer a conexao
            conNoticia2.setReadTimeout(5000);
            //  conData.setInstanceFollowRedirects(false); //sem redirecionamento

            responseCode = conNoticia2.getResponseCode();
            System.out.println("Código de resposta: " + responseCode);

            if (responseCode == 200) {
                //System.out.println("Código de resposta: " + responseCode);
                //deu certo
                BufferedReader in = new BufferedReader(new InputStreamReader(conNoticia2.getInputStream()));
                String inputLine = null;
                while ((inputLine = in.readLine()) != null) {
                    pegaNoticia2(inputLine);
                    //System.out.println(inputLine);
                }
                //fechando os canais de comunicacao
                in.close();
            } else {
                //301 --> movido permanentemente
                if (responseCode > 300 && responseCode <= 302) {
                    System.out.println("Movido permanentemente. Redirecionando...");
                    String location = conNoticia2.getHeaderField("Location");  //pega um campo do cabeçalho
                    System.out.println("foi movido");
                    //openConnectionNoticia1(location); //abre a conexao nessa nova localização
                }

            }

        } catch (MalformedURLException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public final void pegaNoticia2(String linha) {
        Pattern pattern = Pattern.compile("\\s*\\<\\s*h2\\s*class\\=\\\"c-main-headline__title\\\"\\>(.*)\\<\\/h2\\>");
        Matcher matcher = pattern.matcher(linha);

        if (matcher.find()) {
            noticia2.setText("<html><br style=\"width:200px\">"+matcher.group(1)+ " - Fonte: A Folha - Coronavirus" + "</br></html>");
            return;
        }
    }

    public final void openConnetionNoticia3() {
        try {
            URL url = new URL("https://agenciabrasil.ebc.com.br/");

            int responseCode;

            HttpURLConnection conNoticia3 = (HttpURLConnection) url.openConnection();
            conNoticia3.setRequestMethod("GET");
            conNoticia3.setRequestProperty("User-Agent", "Mozilla/5.0");
            conNoticia3.setConnectTimeout(5000); //tempo para estabelecer a conexao
            conNoticia3.setReadTimeout(5000);
            //  conData.setInstanceFollowRedirects(false); //sem redirecionamento

            responseCode = conNoticia3.getResponseCode();
            System.out.println("Código de resposta: " + responseCode);

            if (responseCode == 200) {
                //System.out.println("Código de resposta: " + responseCode);
                //deu certo
                BufferedReader in = new BufferedReader(new InputStreamReader(conNoticia3.getInputStream()));
                String inputLine = null;
                while ((inputLine = in.readLine()) != null) {
                    pegaNoticia3(inputLine);
                    //System.out.println(inputLine);
                }
                //fechando os canais de comunicacao
                in.close();
            } else {
                //301 --> movido permanentemente
                if (responseCode > 300 && responseCode <= 302) {
                    System.out.println("Movido permanentemente. Redirecionando...");
                    String location = conNoticia3.getHeaderField("Location");  //pega um campo do cabeçalho
                    System.out.println("foi movido");
                    //openConnectionNoticia1(location); //abre a conexao nessa nova localização
                }

            }
        } catch (MalformedURLException ex) {
           System.out.println(ex.getMessage());
        } catch (IOException ex) {
           System.out.println(ex.getMessage());
        }
    }

    public final void pegaNoticia3(String linha) {
        Pattern pattern = Pattern.compile("\\<h2\\s*class\\=\\\"col\\s*col-md-10.*\\>(.*)\\<\\/h2>");
        Matcher matcher = pattern.matcher(linha);

        if (matcher.find()) {
            noticia3.setText("<html><br style=\"width:200px\">"+matcher.group(1)+ " - Fonte: Agência Brasil" + "</br></html>");
            return;
        }
    }

}
