package com.cht.mybankapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cht.mybankapp.R
import com.cht.mybankapp.data.model.Account
import com.cht.mybankapp.databinding.ActivityMainBinding
import com.cht.mybankapp.domain.contract.AccountContract
import com.cht.mybankapp.domain.presenter.AccountPresenter

class MainActivity : AppCompatActivity(), AccountContract.View {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    // Презентер для управления логикой работы со счетами
    private lateinit var presenter: AccountContract.Presenter

    private lateinit var adapter: AccountAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Инициализация презентера
        presenter = AccountPresenter(this)

        adapter = AccountAdapter(
            onDelete = { id ->
                presenter.deleteAccount(id)
            },
            onEdit = { account ->
                //show edit dialog
                showEditDialog(account)
            },
            onStatusToggle = { id, isChecked ->
                presenter.updateAccountStatus(id, isChecked)
            }
        )
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        binding.btnAdd.setOnClickListener {
            showAddDialog()
        }

        // Загрузка списка счетов
        presenter.loadAccounts()
    }

    // Отображение списка счетов
    override fun showAccounts(accounts: List<Account>) {
        adapter.submitList(accounts)
    }

    // Отображение сообщения об ошибке
    override fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    // Отображение сообщения об успешной операции
    override fun showSuccess(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    // Показ диалогового окна для добавления нового счета
    private fun showAddDialog() {
        // Создание и настройка пользовательского макета диалога
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_account, null)
        val nameInput = dialogView.findViewById<EditText>(R.id.etName)
        val balanceInput = dialogView.findViewById<EditText>(R.id.etBalance)
        val currencyInput = dialogView.findViewById<EditText>(R.id.etCurrency)

        // Создание и отображение диалогового окна
        AlertDialog.Builder(this)
            .setTitle("Добавить счёт") // Заголовок диалога
            .setView(dialogView) // Установка пользовательского макета
            .setPositiveButton("Добавить") { _, _ ->
                // Получение данных из полей ввода
                val name = nameInput.text.toString()
                val balance = balanceInput.text.toString()
                val currency = currencyInput.text.toString()

                // Вызов метода добавления счета в презентере
                presenter.addAccount(name, balance, currency)
            }
            .setNegativeButton("Отмена", null) // Кнопка отмены
            .show()
    }

    private fun showEditDialog(account: Account) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_account, null)
        val nameInput = dialogView.findViewById<EditText>(R.id.etName)
        val balanceInput = dialogView.findViewById<EditText>(R.id.etBalance)
        val currencyInput = dialogView.findViewById<EditText>(R.id.etCurrency)

        // Заполняем текущими данными
        nameInput.setText(account.name)
        balanceInput.setText(account.balance)
        currencyInput.setText(account.currency)

        AlertDialog.Builder(this)
            .setTitle("Редактировать счёт")
            .setView(dialogView)
            .setPositiveButton("Обновить") { _, _ ->
                val name = nameInput.text.toString()
                val balance = balanceInput.text.toString()
                val currency = currencyInput.text.toString()

                val updated = account.copy(
                    name = name,
                    balance = balance,
                    currency = currency
                )

                presenter.updateAccountFully(updated.id!!, updated)
            }
            .setNegativeButton("Отмена", null)
            .show()
    }

}