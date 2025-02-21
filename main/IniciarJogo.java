
// import javax.swing.*;

// public class IniciarJogo extends JFrame {

//     public static void main(String[] args) {
//         new IniciarJogo();
//     }

//     IniciarJogo() {
//         add(new TelaJogo());
//         setTitle("Jogo da Cobrinha - Snake game");
//         setDefaultCloseOperation(EXIT_ON_CLOSE);
//         setResizable(false);
//         pack();
//         setVisible(true);
//         setLocationRelativeTo(null);
//     }
// }

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

