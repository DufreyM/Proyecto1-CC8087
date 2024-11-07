package uvg.edu.myapp.authentication.domain

interface AuthRepository {
    suspend fun isUserAuthenticated(): Boolean
    suspend fun registerUser(email: String, password: String): Result<Unit>
    suspend fun loginUser(email: String, password: String): Result<Unit>
    fun logoutUser()
}