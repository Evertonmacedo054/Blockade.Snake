import javax.swing.*;

public class IniciarJogo extends JFrame {
    public static void main(String[] args) {
        new TelaInicial(); 
    }

    public IniciarJogo() {
        add(new TelaJogo());
        setTitle("Jogo da Cobrinha - Snake Game");
        setDefaultCloseOperation(EXIT_ON_CLOSE);// indica que o aplicativo deve terminar quando o usuario fecha a janela
        setResizable(false);
        pack(); 
        setLocationRelativeTo(null); 
        setVisible(true); 
    }
}

