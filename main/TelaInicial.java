import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;




public class TelaInicial extends JFrame { 
    private Image imagemDeFundo; 

    public TelaInicial() { 
        setTitle("Jogo da Cobrinha - Snake Game"); 
        setSize(1300, 750); 
        setDefaultCloseOperation(EXIT_ON_CLOSE); 
        setResizable(false); 
        setLocationRelativeTo(null); // Centraliza a janela na tela
        setLayout(new BorderLayout()); // Define o layout da janela 

        
        JPanel painelFundo = new JPanel() { 
            @Override
            protected void paintComponent(Graphics g) { // Sobrescreve o método paintComponent
                super.paintComponent(g);
                if (imagemDeFundo != null) {
                    g.drawImage(imagemDeFundo, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        painelFundo.setLayout(new BorderLayout()); // Define o layout do painel de fundo como BorderLayout

        // Criação do título
        JLabel titulo = new JLabel("JOGO DA COBRINHA", JLabel.CENTER); // Cria um JLabel para o título
        titulo.setFont(new Font("Ink Free", Font.BOLD, 50)); 
        titulo.setForeground(Color.YELLOW); // cor de fundo 
        titulo.setOpaque(true); 
        titulo.setBackground(new Color(0, 0, 0, 150)); // Define a cor de fundo do título com transparência
        painelFundo.add(titulo, BorderLayout.NORTH); // Adiciona o título ao painel de fundo




        // Criação do botão "Jogar"
        JButton botaoPlay = new JButton("JOGAR"); // Cria um botão com o texto "JOGAR"
        botaoPlay.setFont(new Font("Ink Free", Font.BOLD, 50)); 
        botaoPlay.setBackground(new Color(0, 200, 0)); 
        botaoPlay.setForeground(Color.BLACK); 
        botaoPlay.setFocusPainted(false); // Remove a pintura de foco do botão
        botaoPlay.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5)); 
        botaoPlay.setPreferredSize(new Dimension(300, 100)); 

        // Adiciona eventos de mouse ao botão "Jogar"
        botaoPlay.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) { // Quando o mouse entra no botão
                botaoPlay.setBackground(new Color(0, 255, 0)); // Muda a cor de fundo do botão
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) { // Quando o mouse sai do botão
                botaoPlay.setBackground(new Color(0, 200, 0)); // Muda a cor de fundo do botão
            }
        });

        // Adiciona evento de ação ao botão "Jogar"
        botaoPlay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { // Quando o botão é clicado
                iniciarJogo(); 
                
            }
        });

        /*
         * JAILSON 
         */

        // Criação do painel central 
        JPanel painelCentral = new JPanel(); // Cria um JPanel para o painel central
        painelCentral.setLayout(new GridBagLayout()); // Define o layout do painel central como GridBagLayout
        painelCentral.setOpaque(false); // Define que o painel central é transparente
        painelCentral.add(botaoPlay); // Adiciona o botão "Jogar" ao painel central
        painelFundo.add(painelCentral, BorderLayout.CENTER); // Adiciona o painel central ao painel de fundo

        add(painelFundo); // Adiciona o painel de fundo à janela principal
        setVisible(true); // Torna a janela visível
    }

    // Método para iniciar o jogo
    private void iniciarJogo() {
        dispose(); // Fecha a janela atual
        new IniciarJogo(); // Cria uma nova instância da classe IniciarJogo
    }
}

