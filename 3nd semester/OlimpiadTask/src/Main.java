import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {

    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static StringTokenizer tok = null;

    private static String nextLine() throws IOException {
        if (tok == null || !tok.hasMoreTokens()) {
            tok = new StringTokenizer(reader.readLine());
        }
        return tok.nextToken();
    }

    private static int nextInt() throws IOException {
        return Integer.parseInt(nextLine());
    }

    public static void main(String[] args) throws IOException {
        int n = nextInt();
        int m = nextInt();

        int[] articleAccessLevels = new int[n];
        for (int i = 0; i < n; i++) {
            articleAccessLevels[i] = nextInt();
        }

        for (int i = 0; i < m; i++) {
            int userAccessLevel = nextInt();
            int page = nextInt() - 1;
            int size = nextInt();

            int offset = page * size;
            int found = 0;
            List<Integer> articles = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                if (userAccessLevel >= articleAccessLevels[j]) {
                    found++;
                    if (found > offset && found <= offset + size) {
                        articles.add(j);
                    }
                }
            }

            int pagesQuantity = (found % size == 0 ? found / size : found / size + 1);
            System.out.println(articles.size() + " " + found + " " + pagesQuantity);
            if (articles.size() > 0) {
                for (int index: articles) {
                    System.out.print((index + 1) + " ");
                }
                System.out.println();
            }
        }
    }

}
