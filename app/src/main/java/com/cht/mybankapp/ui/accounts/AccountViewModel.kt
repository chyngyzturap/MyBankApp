package com.cht.mybankapp.ui.accounts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cht.mybankapp.data.api.AccountApi
import com.cht.mybankapp.data.model.Account
import com.cht.mybankapp.data.model.PatchAccountStatusDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val accountApi: AccountApi
): ViewModel(){

    private val _accounts = MutableLiveData<List<Account>>()
    val accounts: LiveData<List<Account>> = _accounts

    private val _successMessage = MutableLiveData<String>()
    val successMessage: LiveData<String> = _successMessage

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun loadAccounts() {
        accountApi.getAccounts().enqueue(object : Callback<List<Account>> {
            override fun onResponse(call: Call<List<Account>>, response: Response<List<Account>>) {
                if (response.isSuccessful) {
                    _accounts.value = response.body() ?: emptyList()
                } else {
                    _errorMessage.value = "Ошибка загрузки"
                }
            }

            override fun onFailure(call: Call<List<Account>>, t: Throwable) {
                _errorMessage.value = "Ошибка сети: ${t.message}"
            }
        })
    }

    fun addAccount(name: String, balance: String, currency: String) {
        val account = Account(name = name, balance = balance, currency = currency, isActive = true)

        accountApi.createAccount(account).enqueue(object : Callback<Account> {
            override fun onResponse(call: Call<Account>, response: Response<Account>) {
                if (response.isSuccessful) {
                    _successMessage.value = "Аккаунт добавлен"
                    loadAccounts()
                } else {
                    _errorMessage.value = "Ошибка добавления"
                }
            }

            override fun onFailure(call: Call<Account>, t: Throwable) {
                _errorMessage.value = "Ошибка сети: ${t.message}"
            }
        })
    }

    fun deleteAccount(id: String) {
        accountApi.deleteAccount(id).enqueue(object: Callback<Unit> {
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if (response.isSuccessful) {
                    _successMessage.value = "Удалено"
                    loadAccounts()
                } else {
                    _errorMessage.value = "Ошибка удаления"
                }
            }

            override fun onFailure(call: Call<Unit>, t: Throwable) {
                _errorMessage.value = "Ошибка сети: ${t.message}"
            }

        } )
    }

    fun updateAccountFully(accountId: String, account: Account) {
        accountApi.updateAccountFully(accountId, account).enqueue(object: Callback<Account>{
            override fun onResponse(call: Call<Account>, response: Response<Account>) {
                if (response.isSuccessful) {
                    _successMessage.value = "Успешно обновлен счет"
                    loadAccounts()
                } else {
                    _errorMessage.value = "Ошибка обновления счета"
                }
            }

            override fun onFailure(call: Call<Account>, t: Throwable) {
                _errorMessage.value = "Ошибка сети: ${t.message}"
            }

        })
    }
    fun updateAccountStatus(accountId: String, isActive: Boolean) {
        accountApi.patchAccountStatus(accountId, PatchAccountStatusDTO(isActive)).enqueue(object: Callback<Account>{
            override fun onResponse(call: Call<Account>, response: Response<Account>) {
                if (response.isSuccessful) {
                    _successMessage.value = "Успешно обновлен cтатус счета"
                    loadAccounts()
                } else {
                    _errorMessage.value = "Ошибка обновления статуса счета"
                }
            }

            override fun onFailure(call: Call<Account>, t: Throwable) {
                _errorMessage.value = "Ошибка сети: ${t.message}"
            }

        })
    }
}