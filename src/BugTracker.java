package MYSQLPractice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class BugTracker {

    public static void main(String[] args) {

        try {
            //Create Connection
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/bugtracker", 
                "root", 
                "anmol"
            );

            //Create Statement
            Statement stmt = con.createStatement();
            Scanner sc = new Scanner(System.in);

            while(true) {
                System.out.println("\n=== BUG TRACKING SYSTEM ===");
                System.out.println("1. View Bugs");
                System.out.println("2. Add Bug");
                System.out.println("3. Update Bug Status");
                System.out.println("4. Delete Bug");
                System.out.println("5. Exit");
                System.out.print("Choose: ");

                int ch = sc.nextInt();
                sc.nextLine();

                if(ch == 1) {
                    String q = "SELECT b.bug_id, b.title, p.project_name, e.name, s.status " +
                               "FROM bugs b JOIN projects p ON b.project_id=p.project_id " +
                               "JOIN employees e ON b.created_by=e.emp_id " +
                               "JOIN bug_status s ON b.bug_id=s.bug_id";

                    ResultSet rs = stmt.executeQuery(q);

                    System.out.println("\nID | Title | Project | By | Status");
                    while(rs.next()) {
                        System.out.println(
                            rs.getString(1) + " | " +
                            rs.getString(2) + " | " +
                            rs.getString(3) + " | " +
                            rs.getString(4) + " | " +
                            rs.getString(5)
                        );
                    }
                }

                else if(ch == 2) {
                    System.out.print("Bug ID: ");
                    String id = sc.nextLine();
                    System.out.print("Title: ");
                    String title = sc.nextLine();
                    System.out.print("Description: ");
                    String desc = sc.nextLine();
                    System.out.print("Project ID (A/B): ");
                    String pid = sc.nextLine();
                    System.out.print("Created By (Emp ID): ");
                    int emp = sc.nextInt();
                    sc.nextLine();

                    String insertBug =
                        "INSERT INTO bugs VALUES('" + id + "','" + title + "','" + desc + "','" + pid + "'," + emp + ",CURDATE())";

                    stmt.executeUpdate(insertBug);

                    String insertStatus =
                        "INSERT INTO bug_status VALUES('S" + id + "','" + id + "','Open',CURDATE())";

                    stmt.executeUpdate(insertStatus);

                    System.out.println("Bug Added.");
                }

                else if(ch == 3) {
                    System.out.print("Bug ID: ");
                    String id = sc.nextLine();
                    System.out.print("New Status: ");
                    String status = sc.nextLine();

                    String update =
                        "UPDATE bug_status SET status='" + status + "', updated_on=CURDATE() WHERE bug_id='" + id + "'";

                    stmt.executeUpdate(update);
                    System.out.println("Status Updated.");
                }

                else if(ch == 4) {
                    System.out.print("Bug ID: ");
                    String id = sc.nextLine();

                    stmt.executeUpdate("DELETE FROM bug_status WHERE bug_id='" + id + "'");
                    stmt.executeUpdate("DELETE FROM bugs WHERE bug_id='" + id + "'");

                    System.out.println("Bug Deleted.");
                }

                else if(ch == 5) {
                    con.close();
                    System.out.println("System Closed.");
                    break;
                }
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}
