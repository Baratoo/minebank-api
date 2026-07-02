package minebank_api.service;

import com.fasterxml.jackson.databind.deser.std.NumberDeserializers;
import minebank_api.domain.BankTransaction;
import minebank_api.domain.Player;
import minebank_api.domain.TransactionType;
import minebank_api.domain.Wallet;
import minebank_api.repository.BankTransactionRepository;
import minebank_api.repository.WalletRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class WalletService {
    private final  WalletRepository walletRepository;
    private final BankTransactionRepository bankTransactionRepository;

    public WalletService(WalletRepository walletRepository,  BankTransactionRepository bankTransactionRepository) {
        this.walletRepository = walletRepository;
        this.bankTransactionRepository = bankTransactionRepository;
    }

    public Wallet CreateOrGetWallet(Player player){
        return walletRepository.findByPlayer(player).orElseGet(() -> walletRepository.save(new Wallet(player)));
    }

    public Wallet getWalletByPlayer(Player player){
        return walletRepository.findByPlayer(player).orElseThrow(() -> new RuntimeException("Carteira não encontrada"));
    }

    public void transfer(Player p1, Player p2, BigDecimal amount, TransactionType Type){
        if (amount.compareTo(BigDecimal.ZERO) <= 0){
            throw new RuntimeException("Valor não pode ser 0");
        }

        Wallet walletP1 = getWalletByPlayer(p1);
        Wallet walletP2 = getWalletByPlayer(p2);

        //        if (walletP1.getBalance >= amount) {
        if (walletP1.getBalance().compareTo(amount) < 0){
            throw new RuntimeException("Saldo insuficiente");
        }

        walletP1.debit(amount);
        walletP2.credit(amount);
        walletRepository.save(walletP1);
        walletRepository.save(walletP2);

        BankTransaction bankTransaction = new BankTransaction(p1, p2, amount, Type);
        bankTransactionRepository.save(bankTransaction);
    }



}
