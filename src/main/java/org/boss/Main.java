package org.boss;
//https://ocyan-prod.intelie.com/admin#/integrations/mongodb/369/db-management
//https://ocyan-prod.intelie.com/admin#/integrations/mongodb/386/db-management
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class Main {
    static WebDriver driver;
    static int x;
    private final static int ano = 2022;
    private final static int mes = -1;//Janeiro = 0, Fevereiro = 1, Março = 2, Abril = 3, Maio = 4, Junho = 5, Julho = 6, Agosto = 7, Setembro = 8, Outubro = 9, Novembro = 10, Dezembro = -1
    private final static String pasta = "dez";
    private final static String hibernacao = "desativado"; // ativado ou desativado
    private static Robot robo;

    static {
        try {
            robo = new Robot();
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws InterruptedException, AWTException {

        System.setProperty("webdriver.chrome.driver", "C:\\Users\\christiansouza\\Downloads\\chromedriver_win32 (1)\\chromedriver.exe");
        HashMap<String, Object> chromePrefs = new HashMap<>();
        chromePrefs.put("profile.default_content_settings.popups", 0);
        //fevereiro
        chromePrefs.put("download.default_directory", "C:\\Users\\christiansouza\\IdeaProjects\\IDS_Aquisicao_Pro-Nova_Maven\\archives\\odn2\\"+pasta);
        chromePrefs.put("profile.content_settings.exceptions.automatic_downloads.*.setting", 1);
        chromePrefs.put("downloads.prompt_for_download", false);

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.setExperimentalOption("prefs", chromePrefs);

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(220, TimeUnit.SECONDS);

        //Acesandoa página de login do Pró-Nova
        driver.get("https://login.tde.at/adfs/ls/?wa=wsignin1.0&wtrealm=https%3a%2f%2fpronova.tde.at%2fpetrobras%2f&wctx=rm%3d0%26id%3dpassive%26ru%3d%252fpetrobras%252fHistogramOverview&wct=2023-03-01T05%3a19%3a35Z");
        driver.findElement(By.id("emailInput")).sendKeys("PETROBRAS-ODEBRECHT");
        driver.findElement(By.name("HomeRealmByEmail")).click();

        //Neste momento você é redirecionado para outra página
        driver.findElement(By.id("UserName")).sendKeys("PETROBRAS-ODEBRECHT");
        driver.findElement(By.id("Password")).sendKeys("ctvVvH8w");
        driver.findElement(By.className("button")).click();

        //Acessando a pagina Hitorigram overview
        driver.findElement(By.xpath("//*[@id=\"sidebarMenu\"]/div/ul[1]/li[6]/a")).click();
        //Company
        driver.findElement(By.xpath("//*[@id=\"menuFilterOverlay\"]/div[1]/div/div[2]/div[1]/ul/li[1]/span/span[1]")).click();
        //Petrobras
        driver.findElement(By.xpath("//*[@id=\"menuFilterOverlay\"]/div[1]/div/div[2]/div[1]/ul/li[1]/ul/li/span/span[1]")).click();
        //Ocyan
        driver.findElement(By.xpath("//*[@id=\"menuFilterOverlay\"]/div[1]/div/div[2]/div[1]/ul/li[1]/ul/li/ul/li/span/span[1]")).click();
        //NS-33 NORBE IX
        //driver.findElement(By.xpath("//*[@id=\"menuFilterOverlay\"]/div[1]/div/div[2]/div[1]/ul/li[1]/ul/li/ul/li/ul/li[2]/span/span[2]")).click();
        //NS-41 ODN I
        //driver.findElement(By.xpath("//*[@id=\"menuFilterOverlay\"]/div[1]/div/div[2]/div[1]/ul/li[1]/ul/li/ul/li/ul/li[3]/span/span[2]")).click();
        //NS-42 ODN II
        driver.findElement(By.xpath("//*[@id=\"menuFilterOverlay\"]/div[1]/div/div[2]/div[1]/ul/li[1]/ul/li/ul/li/ul/li[4]/span/span[2]")).click();
        //KPIs
        driver.findElement(By.xpath("//*[@id=\"menuFilterOverlay\"]/div[1]/div/div[2]/div[1]/ul/li[24]/span/span[1]")).click();
        //IDS KPIs
        driver.findElement(By.xpath("//*[@id=\"menuFilterOverlay\"]/div[1]/div/div[2]/div[1]/ul/li[24]/ul/li[1]/span/span[2]")).click();

        //Selecionando a janela de tempo
        //Data de início
        driver.findElement(By.xpath("//*[@id=\"menuFilterOverlayfilterFromPicker\"]/span")).click();
        //Selecionando o mês atual
        anoInicialFinal(2);
        Calendar hoje = Calendar.getInstance();

        // Pega mês atual
        //int mes = hoje.get(Calendar.MONTH)-1;//Janeiro = 0, Fevereiro = 1, Março = 2, Abril = 3, Maio = 4, Junho = 5, Julho = 6, Agosto = 7, Setembro = 8, Outubro = 9, Novembro = 10, Dezembro = -1


        //Seletor do mes
        driver.findElement(By.xpath("//*[@id=\"menuFilterOverlay\"]/div[2]/div[2]/div[1]/table/thead/tr[1]/th[2]/select[1]")).click();

        //Verificando o mês anterior
        selecionaMes(mes, 2);
        //Verificando o primeiro dia do mes
        dataInicialFinal(1, 3, 1, 2);

        //Apply
        driver.findElement(By.xpath("//*[@id=\"menuFilterOverlay\"]/div[2]/div[4]/button[2]")).click();

        //Data final
        driver.findElement(By.xpath("//*[@id=\"menuFilterOverlayfilterToPicker\"]/span")).click();
        anoInicialFinal(3);
        //Seletor do mes
        driver.findElement(By.xpath("//*[@id=\"menuFilterOverlay\"]/div[3]/div[2]/div[1]/table/thead/tr[1]/th[2]/select[1]")).click();

        selecionaMes(mes, 3);

        //Atribuindo os dias das ultimas semanas
        switch (mes) {
            case 0:
            case 2:
            case 4:
            case 6:
            case 7:
            case 9:
            case -1:
                dataInicialFinal(5, 7, 31, 3);
                break;
            case 1:
                dataInicialFinal(4, 7, 28, 3);
                break;
            default:
                dataInicialFinal(5, 7, 30, 3);
                break;
        }

        //Selecionando o horário 23:59
        driver.findElement(By.xpath("//*[@id=\"menuFilterOverlay\"]/div[3]/div[2]/div[2]/select[1]")).click();
        //Hora
        driver.findElement(By.xpath("//*[@id=\"menuFilterOverlay\"]/div[3]/div[2]/div[2]/select[1]/option[24]")).click();
        driver.findElement(By.xpath("//*[@id=\"menuFilterOverlay\"]/div[3]/div[2]/div[2]/select[2]")).click();
        //Minuto
        driver.findElement(By.xpath("//*[@id=\"menuFilterOverlay\"]/div[3]/div[2]/div[2]/select[2]/option[60]")).click();

        //Apply
        driver.findElement(By.xpath("//*[@id=\"menuFilterOverlay\"]/div[3]/div[4]/button[2]")).click();
        Thread.sleep(2000);
        //Apply
        driver.findElement(By.xpath("//*[@id=\"btnContainer\"]/input[1]")).click();
        Thread.sleep(10000);

        ArrayList<String> nomeArq = new ArrayList();
        ArrayList<Integer> posArq = new ArrayList();
        for(int i=1; i<40; i++){
            driver.findElement(By.xpath("//*[@id=\"FilteredKpiDescriptioncontainerId\"]/div["+i+"]/div[1]")).click();
            Thread.sleep(12000);
            String res = driver.findElement(By.xpath("//*[@id=\"FilteredKpiDescriptioncontainerId\"]/div["+i+"]/div[2]/div")).getText();
            String nome = driver.findElement(By.xpath("//*[@id=\"FilteredKpiDescriptioncontainerId\"]/div["+i+"]/div[1]/h3/span[2]")).getText();
            Click(1);
            if (!"No Data available for your current Selection".equals(res)){
                nomeArq.add(nome);
                posArq.add(i);
                System.out.println(i+" - "+nome);
            }
            driver.findElement(By.xpath("//*[@id=\"FilteredKpiDescriptioncontainerId\"]/div["+i+"]/div[1]")).click();
        }
        System.out.println(" ");
        System.out.println(posArq.size()+" Arquivos -> "+posArq);
        System.out.println("Transferindo arquivos...");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        for (int c = 0; c < posArq.size(); c++) {
            Click(1);
            int p = posArq.get(c);
            WebElement element = driver.findElement(By.xpath("//*[@id=\"FilteredKpiDescriptioncontainerId\"]/div["+p+"]/div[1]/div/i"));
            js.executeScript("arguments[0].click();", element);
            WebElement download = driver.findElement(By.xpath("//*[@id=\"FilteredKpiDescriptioncontainerId\"]/div["+p+"]/div[1]/div/ul/li[1]/a"));
            js.executeScript("arguments[0].click();", download);
            Thread.sleep(17000);
        }
        System.out.println("Arquivos transferidos com sucesso para a pasta "+pasta);
        //driver.quit();
    }
    public static void anoInicialFinal(int mod) throws InterruptedException { //mod=2 -> ano início, mod=3 -> ano fim
        int anoIndex = ano - 1922;
        driver.findElement(By.xpath("//*[@id=\"menuFilterOverlay\"]/div["+mod+"]/div[2]/div[1]/table/thead/tr[1]/th[2]/select[2]")).click();
        driver.findElement(By.xpath("//*[@id=\"menuFilterOverlay\"]/div["+mod+"]/div[2]/div[1]/table/thead/tr[1]/th[2]/select[2]/option["+anoIndex+"]")).click();
        Thread.sleep(1000);
    }
    public static void dataInicialFinal(int inf,int sup, int data, int mod){//mod = 2 -> início do mês, mod = 3 -> final do mês
        x=0;
        for (int i = inf; i<sup; i++) {
            if(x==1){
                break;
            }
            for (int j=1; j<8; j++) {
                int dia = Integer.parseInt(driver.findElement(By.xpath("//*[@id=\"menuFilterOverlay\"]/div["+mod+"]/div[2]/div[1]/table/tbody/tr["+i+"]/td["+j+"]")).getText());
                if (dia == data){
                    driver.findElement(By.xpath("//*[@id=\"menuFilterOverlay\"]/div["+mod+"]/div[2]/div[1]/table/tbody/tr["+i+"]/td["+j+"]")).click();
                    x=1;
                    break;
                }
            }
        }
    }
    public static void selecionaMes(int mes, int mod) {////mod = 2 -> mês inicial, mod = 3 -> mês final
        int certo = mes + 1;
        if(mes!=-1){
            driver.findElement(By.xpath("//*[@id=\"menuFilterOverlay\"]/div["+mod+"]/div[2]/div[1]/table/thead/tr[1]/th[2]/select[1]/option["+certo+"]")).click();
        } else {
            driver.findElement(By.xpath("//*[@id=\"menuFilterOverlay\"]/div["+mod+"]/div[2]/div[1]/table/thead/tr[1]/th[2]/select[1]/option[12]")).click();
        }
    }
    public static void Click(int i){
        if (hibernacao.equals("desativado")){
            robo.mouseMove(262,265);
            while (i != 0){
                robo.delay(10);
                robo.mousePress(KeyEvent.BUTTON1_DOWN_MASK);
                robo.mouseRelease(KeyEvent.BUTTON1_DOWN_MASK);
                --i;
            }
            robo.delay(500);
        }
    }
}