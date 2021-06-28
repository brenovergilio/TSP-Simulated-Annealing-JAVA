import java.util.List;

public class Coordenada {
    public double x;
    public double y;

    Coordenada(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public static double distancia_total(List<Coordenada> coords) {
        double distancia = 0;
        int i = 0;
        int j = 1;

        while(j<coords.size()) {
            distancia += Coordenada.distancia(coords.get(i), coords.get(j));
            i++;
            j++;
        }

        distancia += Coordenada.distancia(coords.get(0), coords.get(coords.size()-1));
        return distancia;
    }

    private static double distancia(Coordenada a, Coordenada b) {
        return Math.sqrt(Math.pow((a.x-b.x), 2) + Math.pow((a.y-b.y), 2));
    }
}
