package com.cht.mybankapp.ui.account_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.cht.mybankapp.R
import com.cht.mybankapp.data.model.Account
import com.cht.mybankapp.databinding.ActivityAccountDetailsBinding

class AccountDetailsActivity : AppCompatActivity() {

    private var _binding: ActivityAccountDetailsBinding? = null
    private val binding get() = _binding!!

    private val accountId: String by lazy { intent.getStringExtra("account_id") ?: "" }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAccountDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        Toast.makeText(this, "ID счета: $accountId", Toast.LENGTH_SHORT).show()

    }

    private fun initClicks(){
        with(binding){
            btnEdit.setOnClickListener {
//                showEditDialog(viewModel.account.value)
            }
            btnDelete.setOnClickListener {

            }
        }
    }

    private fun subscribeToLiveData(){

    }

    private fun setAccountInfo(account: Account?) {
        with(binding){
            account?.let {
                tvName.text = "Название: ${it.name}"
                tvIsActive.text = "Статус активности: ${it.isActive.toString()}"
                tvBalance.text = "Баланс: ${it.balance}"
                tvCurrency.text = "Валюта: ${it.currency}"
            }
        }
    }

    private fun showEditDialog(account: Account?) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_account, null)
        val nameInput = dialogView.findViewById<EditText>(R.id.etName)
        val balanceInput = dialogView.findViewById<EditText>(R.id.etBalance)
        val currencyInput = dialogView.findViewById<EditText>(R.id.etCurrency)

        nameInput.setText(account?.name)
        balanceInput.setText(account?.balance)
        currencyInput.setText(account?.currency)

        AlertDialog.Builder(this)
            .setTitle("Редактировать счёт")
            .setView(dialogView)
            .setPositiveButton("Обновить") { _, _ ->
                val name = nameInput.text.toString()
                val balance = balanceInput.text.toString()
                val currency = currencyInput.text.toString()

                val updatedAccount = account?.copy(
                    name = name,
                    balance = balance,
                    currency = currency
                )

//                viewModel.updateAccountFully(accountId, updated)

            }
            .setNegativeButton("Отмена", null)
            .show()
    }
}