package kadai_010;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Scores_Chapter10 {
	public static void main(String[] args) {
		Connection con = null;
		Statement statement = null;
		try {
			// データベース接続
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost/challenge_java",
					"root",
					"root");
			System.out.println("データベース接続成功" + con);

			// ステートメント作成
			statement = con.createStatement();

			// Step3: 武者小路勇気さんの点数を更新
			String updateSql = "UPDATE scores SET score_math = 95, score_english = 80 WHERE id = 5";
			int updated = statement.executeUpdate(updateSql);
			System.out.println("更新された行数: " + updated);

			// Step4: 点数順に並べ替えて取得（数学→英語）
			String selectSql = "SELECT * FROM scores ORDER BY score_math DESC, score_english DESC;";
			ResultSet rs = statement.executeQuery(selectSql);
			int count = 1;
			
			// rs.point = -1
			// ts.getInt()
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				int math = rs.getInt("score_math");
				int english = rs.getInt("score_english");

				System.out.println(count + "件目：生徒ID=" + id + "／氏名=" + name + "／数学=" + math + "／英語=" + english);
				count++;
			}

			// リソースを閉じる
			rs.close();
			statement.close();
			con.close();

		} catch (SQLException e) {
			System.out.println("エラーが発生しました: " + e.getMessage());
			e.printStackTrace();
		} finally {
			// finallyで確実に接続を閉じる
			try {
				if (statement != null)
					statement.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				System.out.println("接続のクローズ時にエラーが発生しました: " + e.getMessage());
			}
		}
	}
}
