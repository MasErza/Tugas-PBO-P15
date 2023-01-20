// proses import liblary
import java.sql.*;
import java.util.Scanner;

//membuat clas main utama untuk koneksi ke db
public class Main {
    // dipergunakan untuk melakukan koneksi mysql dengan vscode serta program
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/db_mahasiswa";
    static final String USER = "root";
    static final String PASS = "";

    // membuat main untuk program run
    public static void main(String[] args) {
        // Kode ini digunakan untuk menghubungkan data ke sebuah database menggunakan
        // JDBC di Java
        // Kode Connection untuk koneksi dan Statement dipergunakan sebagai eksekutor
        Connection koneksi = null;
        Statement eksekusi = null;
        try {
            Class.forName(JDBC_DRIVER);
            koneksi = DriverManager.getConnection(DB_URL, USER, PASS);
            eksekusi = koneksi.createStatement();
            // membuat menu
            Scanner input = new Scanner(System.in);
            int pilihan;
            do {
                System.out.println("=======================================");
                System.out.println("Aplikasi CRUD Data Mahasiswa:");
                System.out.println("=======================================");
                System.out.println("1. Tampilkan data Mahasiswa");
                System.out.println("2. Input data Mahasiswa");
                System.out.println("3. Edit data Mahasiswa");
                System.out.println("4. Hapus data Mahasiswa");
                System.out.println("5. Keluar");
                System.out.println("=======================================");
                System.out.print("Masukkan pilihan: ");
                pilihan = input.nextInt();

                switch (pilihan) {
                    case 1:
                        showAll(eksekusi);
                        break;
                    case 2:
                        create(eksekusi, input);
                        break;
                    case 3:
                        edit(eksekusi, input);
                        break;
                    case 4:
                        delete(eksekusi, input);
                        break;
                    case 5:
                        System.out.println("Terima kasih telah menggunakan aplikasi ini.");
                        break;
                    default:
                        System.out.println("Pilihan tidak valid. Silakan coba lagi.");
                        break;
                }
            } while (pilihan != 5);

            eksekusi.close();
            koneksi.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (eksekusi != null)
                    eksekusi.close();
            } catch (SQLException se2) {
            }
            try {
                if (koneksi != null)
                    koneksi.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    private static void showAll(Statement eksekusi) throws SQLException {
        String sql = "SELECT * FROM mahasiswa";
        ResultSet rs = eksekusi.executeQuery(sql);
        System.out.println("-----------------------------------------");
        System.out.println("Data Mahasiswa:");
        System.out.println("-----------------------------------------");
        while (rs.next()) {
            // ngambil data di database kumudian di tampilkan
            int nim = rs.getInt("nim");
            String nama = rs.getString("nama");
            System.out.println("NIM : " + nim + "| Nama : " + nama);
        }
        System.out.println();
    }

    private static void create(Statement eksekusi, Scanner input) throws SQLException {
        System.out.print("Masukkan NIM: ");
        int nim = input.nextInt();
        input.nextLine();
        System.out.print("Masukkan Nama Mahasiswa: ");
        String nama = input.nextLine();

        String sql = "INSERT INTO mahasiswa (nim, nama) VALUES (" + nim + ", '" + nama + "')";
        eksekusi.executeUpdate(sql);
        System.out.println("-----------------------------------------");
        System.out.println("Data Mahasiswa berhasil ditambahkan.");
        System.out.println("-----------------------------------------\n");
    }

    private static void edit(Statement eksekusi, Scanner input) throws SQLException {
        System.out.print("Masukkan NIM mahasiswa yang akan diubah: ");
        int nim = input.nextInt();
        input.nextLine();
        System.out.print("Masukkan nama baru: ");
        String nama = input.nextLine();

        String sql = "UPDATE mahasiswa SET nama = '" + nama + "' WHERE nim = " + nim;
        eksekusi.executeUpdate(sql);
        System.out.println("-----------------------------------------");
        System.out.println("Data mahasiswa berhasil diubah.");
        System.out.println("-----------------------------------------\n");
    }

    private static void delete(Statement eksekusi, Scanner input) throws SQLException {
        System.out.print("Masukkan NIM mahasiswa yang akan dihapus: ");
        int nim = input.nextInt();

        String sql = "DELETE FROM mahasiswa WHERE nim = " + nim;
        eksekusi.executeUpdate(sql);
        System.out.println("-----------------------------------------");
        System.out.println("Data mahasiswa berhasil dihapus.");
        System.out.println("-----------------------------------------\n");
    }
}
