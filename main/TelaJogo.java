import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class TelaJogo extends JPanel implements ActionListener {

    private static final int LARGURA_TELA = 1300;
    private static final int ALTURA_TELA = 750;
    private static final int TAMANHO_BLOCO = 35;
    private static final int UNIDADES = LARGURA_TELA * ALTURA_TELA / (TAMANHO_BLOCO * TAMANHO_BLOCO);
    private static final int INTERVALO = 200;
    private static final String NOME_FONTE = "Ink Free";
    private final int[] eixoX = new int[UNIDADES];
    private final int[] eixoY = new int[UNIDADES];
    private int corpoCobra = 6;
    private int blocosComidos;
    private int blocoX;
    private int blocoY;
    private char direcao = 'D'; // C - Cima, B - Baixo, E - Esquerda, D - Direita
    private boolean estaRodando = false;
    private Clip somFundo;
    private Clip somComer;
    private Clip somMorrer;
    private Clip somDePause;
    private boolean estaPausado = false;
    private int velocidadeAtual = INTERVALO;
    private Image imagemDeFundo;
    private Image imagemDeMaca;

    Timer timer;
    Random random;

    TelaJogo() {
        random = new Random();
        setPreferredSize(new Dimension(LARGURA_TELA, ALTURA_TELA));
        setBackground(Color.darkGray);
        setFocusable(true);
        addKeyListener(new KeyReaderAdapter());
        carregarImagemDeFundo();
        carregarImagemDeMaca();
        
        carregarSons();
        iniciarJogo();
    }
    
    //imagem fundo 
    private void carregarImagemDeFundo() {
        ImageIcon icone = new ImageIcon("main/img/fundo.jpg");
        imagemDeFundo = icone.getImage();
    }
    // imagem apple
    private void carregarImagemDeMaca() {
        ImageIcon iconeMaca = new ImageIcon("main/img/apple.png");
        imagemDeMaca = iconeMaca.getImage();
    }
    
    
    // carregar som
    private void carregarSons() {
        somFundo = SomUtils.carregarSom("main/sons/som_fundo.wav");
        somComer = SomUtils.carregarSom("main/sons/som_alimento.wav");
        somMorrer = SomUtils.carregarSom("main/sons/som_perdeu.wav");
        somDePause = SomUtils.carregarSom("main/sons/som_pause.wav");
        SomUtils.ajustarVolume(somFundo, -20.0f);
        SomUtils.ajustarVolume(somDePause, -20.0f);
    }

    public void iniciarJogo() {
        criarBloco();
        estaRodando = true;
        velocidadeAtual = INTERVALO;
        timer = new Timer(velocidadeAtual, this);
        timer.start();
        SomUtils.tocarSomLoop(somFundo);
    }

    public void reiniciarJogo() {
        corpoCobra = 6;
        blocosComidos = 0;
        direcao = 'D';
        
        for (int i = 0; i < corpoCobra; i++) {
            eixoX[i] = 0;
            eixoY[i] = 0;
        }

        eixoX[0] = 0;
        eixoY[0] = 0;
        estaRodando = true;
        criarBloco();
        velocidadeAtual = INTERVALO;
        timer.setDelay(velocidadeAtual);
        timer.restart();
        SomUtils.tocarSomLoop(somFundo);
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        desenharImagemDeFundo(g);
        if (estaPausado) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Ink Free", Font.BOLD, 75));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Jogo Pausado", (LARGURA_TELA - metrics.stringWidth("Jogo Pausado")) / 2, ALTURA_TELA / 2);
        }
        desenharTela(g);
    }

    private void desenharImagemDeFundo(Graphics g) {
        if (imagemDeFundo != null) {
            g.drawImage(imagemDeFundo, 0, 0, LARGURA_TELA, ALTURA_TELA, this);
        }
    }
    public void desenharTela(Graphics g) {
        if (estaRodando) {
            if (imagemDeMaca != null) {
                
                // g.drawImage(imagemDeMaca, blocoX, blocoY, TAMANHO_BLOCO , TAMANHO_BLOCO,this);
                
                g.drawImage(imagemDeMaca, blocoX, blocoY, TAMANHO_BLOCO * 2, TAMANHO_BLOCO * 2, this);

            }
    
            for (int i = 0; i < corpoCobra; i++) {
                if (i == 0) {
                    g.setColor(Color.green);
                    g.fillRect(eixoX[i], eixoY[i], TAMANHO_BLOCO, TAMANHO_BLOCO);
                } else {
                    g.setColor(new Color(45, 180, 0));
                    g.fillRect(eixoX[i], eixoY[i], TAMANHO_BLOCO, TAMANHO_BLOCO);
                }
            }
    
            g.setColor(Color.red);
            g.setFont(new Font(NOME_FONTE, Font.BOLD, 40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Pontos: " + blocosComidos, (LARGURA_TELA - metrics.stringWidth("Pontos: " + blocosComidos)) / 2, g.getFont().getSize());
        } else {
            fimDeJogo(g);
        }
        
    }
    
    
/*
 * MATHEUS
 */
private void criarBloco() {
    blocoX = random.nextInt(LARGURA_TELA / TAMANHO_BLOCO) * TAMANHO_BLOCO;
    blocoY = random.nextInt(ALTURA_TELA / TAMANHO_BLOCO) * TAMANHO_BLOCO;
}



public void fimDeJogo(Graphics g) {
    SomUtils.pararSom(somFundo);
    SomUtils.tocarSom(somMorrer);

    g.setColor(Color.red);
    g.setFont(new Font(NOME_FONTE, Font.BOLD, 40));
    FontMetrics fontePontuacao = getFontMetrics(g.getFont());
    g.drawString("Pontos: " + blocosComidos, 
        (LARGURA_TELA - fontePontuacao.stringWidth("Pontos: " + blocosComidos)) / 2, 
        g.getFont().getSize());

    SwingUtilities.invokeLater(() -> { 
        int resposta = JOptionPane.showOptionDialog(
            SwingUtilities.getWindowAncestor(this), // Centraliza na janela do jogo
            "Você perdeu! Deseja jogar novamente?",
            "Fim de Jogo",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            new String[]{"Jogar Novamente", "Encerrar"},
            "Jogar Novamente"
        );

        if (resposta == JOptionPane.YES_OPTION) {
            reiniciarJogo();
        } else {
            System.exit(0);
        }
    });
}


    public void actionPerformed(ActionEvent e) {
        if (estaRodando) {
            andar();
            alcancarBloco();
            validarLimites();
        }
        repaint();
    }

    private void andar() {
        for (int i = corpoCobra; i > 0; i--) {
            eixoX[i] = eixoX[i - 1];
            eixoY[i] = eixoY[i - 1];
        }

        switch (direcao) {
            case 'C':
                eixoY[0] = eixoY[0] - TAMANHO_BLOCO;
                break;
            case 'B':
                eixoY[0] = eixoY[0] + TAMANHO_BLOCO;
                break;
            case 'E':
                eixoX[0] = eixoX[0] - TAMANHO_BLOCO;
                break;
            case 'D':
                eixoX[0] = eixoX[0] + TAMANHO_BLOCO;
                break;
            default:
                break;
        }
    }

    private void alcancarBloco() {
        // Verifica a colisão considerando que a maçã tem o dobro do tamanho
        if (Math.abs(eixoX[0] - blocoX) < TAMANHO_BLOCO * 2 && Math.abs(eixoY[0] - blocoY) < TAMANHO_BLOCO * 2) {
            corpoCobra++;
            blocosComidos++;
            SomUtils.tocarSom(somComer);
            criarBloco();
        
            if (velocidadeAtual > 50) {
                velocidadeAtual -= 5;
                timer.setDelay(velocidadeAtual);
            }
        }
    }

    
    
    
    private void validarLimites() {
        //A cabeça bateu no corpo?
        for (int i = corpoCobra; i > 0; i--) {
            if (eixoX[0] == eixoX[i] && eixoY[0] == eixoY[i]) {
                estaRodando = false;
                break;
            }
        }

        //A cabeça tocou uma das bordas Direita ou esquerda?
        if (eixoX[0] < 0 || eixoX[0] > LARGURA_TELA - 50) {
            estaRodando = false;
        }

        //A cabeça tocou o piso ou o teto?
        if (eixoY[0] < 0 || eixoY[0]+TAMANHO_BLOCO > ALTURA_TELA) {
            estaRodando = false;
        }

        if (!estaRodando) {
            timer.stop();
        }
    }

public class KeyReaderAdapter extends KeyAdapter {
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:if (direcao != 'D') { // Impede que a cobra vá para a direita quando já está indo para a esquerda
                    direcao = 'E';
                }
                break;
            case KeyEvent.VK_RIGHT:if (direcao != 'E') { // Impede que a cobra vá para a esquerda quando já está indo para a direita
                    direcao = 'D';
                }
                break;
            case KeyEvent.VK_UP:
                if (direcao != 'B') { // Impede que a cobra vá para baixo quando já está indo para cima
                    direcao = 'C';
                }
                break;
            case KeyEvent.VK_DOWN:
                if (direcao != 'C') { // Impede que a cobra vá para cima quando já está indo para baixo
                    direcao = 'B';
                }
                break;
            case KeyEvent.VK_ESCAPE:
                estaPausado = !estaPausado;
                if (estaPausado) {
                    timer.stop();
                    SomUtils.pausarSom(somFundo);
                    SomUtils.somDePause(somDePause);
                } else {
                    timer.start();
                    SomUtils.continuarSom(somFundo);
                    SomUtils.somDePauseParado(somDePause);
                }
                repaint();
                break;
        }
    }
}
}