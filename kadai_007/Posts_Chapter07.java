package kadai_007;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Posts_Chapter07 {
  public static void main(String[] args) {

    Connection con = null;
    Statement stmt = null;

    try {
      // データベースに接続
      con = DriverManager.getConnection(
          "jdbc:mysql://localhost/challenge_java",
          "root",
          "root");

      System.out.println("データベース接続成功：" + con);

      stmt = con.createStatement();

      System.out.println("レコード追加を実行します");

      String insertSql = """
          INSERT INTO posts (user_id, posted_at, post_content, likes)
          VALUES
            (1003, '2023-02-08', '昨日の夜は徹夜でした・・', 13),
            (1002, '2023-02-08', 'お疲れ様です！', 12),
            (1003, '2023-02-09', '今日も頑張ります！', 18),
            (1001, '2023-02-09', '無理は禁物ですよ！', 17),
            (1002, '2023-02-10', '明日から連休ですね！', 20);
          """;

      int insertCount = stmt.executeUpdate(insertSql);
      System.out.println(insertCount + "件のレコードが追加されました");

      // --- Step 4: データ検索 ---
      System.out.println("ユーザーIDが1002のレコードを検索しました");

      String selectSql = "SELECT posted_at, post_content, likes FROM posts WHERE user_id = 1002";

      ResultSet result = stmt.executeQuery(selectSql);

      int count = 1;
      while (result.next()) {
        Date postedAt = result.getDate("posted_at");
        String content = result.getString("post_content");
        int likes = result.getInt("likes");

        System.out.println(count + "件目：投稿日時=" + postedAt + "／投稿内容=" + content + "／いいね数=" + likes);
        count++;
      }

    } catch (SQLException e) {
      System.out.println("エラー発生：" + e.getMessage());
    } finally {
      // リソースの解放
      try {
        if (stmt != null)
          stmt.close();
        if (con != null)
          con.close();
      } catch (SQLException ignore) {
      }
    }
  }
}
