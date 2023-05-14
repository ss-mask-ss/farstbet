package mask.inc.farstbet;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public final class Farst_bet extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("Farst_bet has been enabled!");

        // プレイヤーがベッドに入ったか監視するタスクを開始
        new SleepCheckTask().runTaskTimer(this, 0, 20);
    }

    @Override
    public void onDisable() {
        getLogger().info("Farst_bet has been disabled!");
    }

    private class SleepCheckTask extends BukkitRunnable {

        private int countdown = 30; // ベッドに入るまでのカウントダウン

        @Override
        public void run() {
            World world = Bukkit.getWorld("world"); // ワールド名を適宜変更

            for (Player player : world.getPlayers()) {
                if (player.isSleeping()) {
                    // プレイヤーがベッドに入っている場合
                    player.sendMessage("§6ベッドで寝ました！");
                    player.setHealth(20); // プレイヤーの体力を回復
                    cancel(); // タスクを終了
                    return;
                }
            }

            countdown--;

            if (countdown <= 0) {
                // カウントダウンが終了した場合、プレイヤーをスペクテータモードに変更
                for (Player player : world.getPlayers()) {
                    player.setGameMode(GameMode.SPECTATOR);
                    player.sendMessage("§cベッドに入らなかったため死亡しました！");
                }

                cancel(); // タスクを終了
            } else {
                // カウントダウン中のメッセージを全体チャットに表示
                Bukkit.broadcastMessage("§eベッドに入るまで " + countdown + " 秒");
            }
        }
    }
}
