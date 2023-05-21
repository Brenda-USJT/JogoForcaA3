package com.mycompany.jogoforcaa3;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Stack;

public class JogoForca extends JFrame {

    private JLabel palavraLabel;
    private JLabel tentativasLabel;
    private JLabel tentativasRestantesLabel;
    private JTextArea boneco;
    private JTextField letraTextField;
    private JButton tentarButton;

    private LinkedList<Character> palavra;
    private Stack<Character> tentativas;
    private int tentativasRestantes;

    public JogoForca() {
        super("Jogo da Forca");
        palavra = new LinkedList<>();
        tentativas = new Stack<>();
        tentativasRestantes = 6;
        inicializarInterface();
        carregarPalavra();
    }

    private void inicializarInterface() {
        palavraLabel = new JLabel();
        tentativasLabel = new JLabel();
        tentativasRestantesLabel = new JLabel();
        boneco = new JTextArea();
        letraTextField = new JTextField();
        tentarButton = new JButton("Tentar");

        palavraLabel.setFont(new Font("Arial", Font.BOLD, 24));
        tentativasLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        tentativasRestantesLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        boneco.setFont(new Font("Arial",Font.BOLD,20));

        JPanel painel = new JPanel(new GridLayout(5, 1));
        painel.add(palavraLabel);
        painel.add(tentativasLabel);
        painel.add(tentativasRestantesLabel);
        painel.add(letraTextField);
        painel.add(tentarButton);
        painel.add(boneco);

        add(painel);

        setSize(300, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        tentarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tentarLetra();
            }
        });
    }

    private void carregarPalavra() {
        String palavraString = JOptionPane.showInputDialog("Digite a palavra:");
        for (char c : palavraString.toCharArray()) {
            palavra.add(c);
        }
        atualizarPalavraLabel();
    }

    private void atualizarPalavraLabel() {
        StringBuilder sb = new StringBuilder();
        for (Character c : palavra) {
            if (tentativas.contains(c)) {
                sb.append(c).append(" ");
            } else {
                sb.append("_ ");
            }
        }
        palavraLabel.setText(sb.toString());
    }

    private void tentarLetra() {
        String letra = letraTextField.getText().toUpperCase();
        if (letra.length() == 1) {
            char c = letra.charAt(0);
            if (!tentativas.contains(c)) {
                tentativas.push(c);
                if (!palavra.contains(c)) {
                    JOptionPane.showMessageDialog(this, "Letra incorreta!");
                    tentativasRestantes--;
                }
            } else {
                JOptionPane.showMessageDialog(this, "Letra já tentada!");
            }
            letraTextField.setText("");
            atualizarPalavraLabel();
            atualizarTentativasLabel();
            verificarFimJogo();
        }
    }

    private void atualizarTentativasLabel() {
        StringBuilder sb = new StringBuilder("Tentativas: ");
        for (Character c : tentativas) {
            sb.append(c).append(" ");
        }
        tentativasLabel.setText(sb.toString());
        tentativasRestantesLabel.setText("Tentativas restantes: " + tentativasRestantes);
    }
    
    private void bonecoForca() {
        switch(tentativasRestantes) {
            case 5:
                boneco.setText("   O");
            case 4:
                boneco.setText("   O\n    |");
            case 3:
                boneco.setText("   O\n    |\"");
            case 2:
                boneco.setText("   O\n     /|\"");
            case 1:
                boneco.setText("   O\n     /|\\n     /");
            case 0:
                boneco.setText("   O\n     /|\\n     /");
                
                
        }
    }
    
    private void verificarFimJogo() {
        boolean palavraCompleta = true;
        for (Character c : palavra) {
            if (!tentativas.contains(c)) {
                palavraCompleta = false;
                break;
            }
        }
        if (palavraCompleta) {
            JOptionPane.showMessageDialog(this, "Parabéns! Você acertou a palavra!");
            reiniciarJogo();
        } else if (tentativasRestantes == 0) {
            JOptionPane.showMessageDialog(this, "Você perdeu! A palavra era: " + palavraToString());
            reiniciarJogo();
        }
    }

    private void reiniciarJogo() {
        int resposta = JOptionPane.showConfirmDialog(this, "Deseja jogar novamente?");
        if (resposta == JOptionPane.YES_OPTION) {
            palavra.clear();
            tentativas.clear();
            tentativasRestantes = 6;
            carregarPalavra();
            tentativasLabel.setText("Tentativas: ");
            tentativasRestantesLabel.setText("Tentativas restantes: " + tentativasRestantes);
        } else {
            System.exit(0);
        }
    }

    private String palavraToString() {
        StringBuilder sb = new StringBuilder();
        for (Character c : palavra) {
            sb.append(c);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new JogoForca();
            }
        });
    }
}
