import java.io.*;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);

        System.out.printf("Insira o fator de decaimento: ");
        double factor = sc.nextDouble();
        System.out.println();

        System.out.printf("Insira a temperatura máxima: ");
        double tMax = sc.nextDouble();
        System.out.println();

        System.out.printf("Insira a temperatura mínima: ");
        double tMin = sc.nextDouble();
        System.out.println();

        System.out.printf("Insira a quantidade de iterações internas: ");
        int it = sc.nextInt();
        System.out.println();

        String path = System.getProperty("user.dir");
        File in = new File(path + "/entradas");

        String [] entradas = in.list();

        for(String s : entradas) {
            List<Coordenada> coords = new ArrayList<Coordenada>();
            read(path ,"/entradas/" + s, coords);
            double custo = simulated_annealing(factor, tMax, tMin, it, coords);
            write(path, s, custo);
        }
    }

    private static double simulated_annealing(double factor, double tMax, double tMin, int it, List<Coordenada> coords) {
        double custoAtual = Coordenada.distancia_total(coords);
        double tAtual = tMax;
        Random random = new Random();

        while(tAtual >= tMin) {
            System.out.printf("Custo atual: %.4f\n", custoAtual);

            for(int i=0; i<it; i++) {
                int r1 = random.nextInt(coords.size());
                int r2 = random.nextInt(coords.size());

                if(r1 == r2) continue;

                swap(coords, r1, r2);
                double custoNovo = Coordenada.distancia_total(coords);

                if(custoNovo < custoAtual) custoAtual = custoNovo;
                else {
                    double x = random.nextDouble();
                    if(x < Math.exp((custoAtual - custoNovo) / tAtual)) custoAtual = custoNovo;
                    else {
                        swap(coords, r1, r2);
                    }
                }
            }
            tAtual = tAtual * factor;
        }

        return custoAtual;
    }

    private static void swap(List<Coordenada> arr, int i, int y){
        Coordenada temp = arr.get(i);
        arr.set(i, arr.get(y));
        arr.set(y, temp);
    }

    private static void read(String path, String file, List<Coordenada> coords) throws IOException {
        Scanner scanner = new Scanner(new FileReader(path + file));
        while (scanner.hasNext()) {
            scanner.next();
            double c1 = Double.parseDouble(scanner.next());
            double c2 = Double.parseDouble(scanner.next());
            coords.add(new Coordenada(c1, c2));
        }
    }

    private static void write(String path, String file, double custo) throws IOException {
        FileWriter fw = new FileWriter(path + "/saidas.txt", true);
        PrintWriter pw = new PrintWriter(fw);
        pw.printf("%s -> %.4f\n", file, custo);
        fw.close();
    }
}
