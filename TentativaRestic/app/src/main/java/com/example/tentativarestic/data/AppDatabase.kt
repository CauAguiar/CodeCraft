package com.example.tentativarestic.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.tentativarestic.dao.*
import com.example.tentativarestic.entities.*

@Database(
    entities = [
        Atividade::class,
        Curso::class,
        ExercicioAberto::class,
        Modulo::class,
        PerguntasQuestionario::class,
        Person::class,
        PersonCurso::class,
        Projeto::class,
        Quiz::class,
        RespostasQuestionario::class,
        RespostasUsuarioQuestionario::class,
        Unidade::class,
        Video::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun atividadeDao(): AtividadeDao
    abstract fun cursoDao(): CursoDao
    abstract fun exercicioAbertoDao(): ExercicioAbertoDao
    abstract fun moduloDao(): ModuloDao
    abstract fun perguntasQuestionarioDao(): PerguntasQuestionarioDao
    abstract fun personDao(): PersonDao
    abstract fun personCursoDao(): PersonCursoDao
    abstract fun projetoDao(): ProjetoDao
    abstract fun quizDao(): QuizDao
    abstract fun respostasQuestionarioDao(): RespostasQuestionarioDao
    abstract fun respostasUsuarioQuestionarioDao(): RespostasUsuarioQuestionarioDao
    abstract fun unidadeDao(): UnidadeDao
    abstract fun videoDao(): VideoDao

    companion object {
        // Volatile para garantir que mudanças na instância sejam visíveis entre threads
        @Volatile
        private var INSTANCE: AppDatabase? = null

        // Método para acessar a instância única do banco de dados
        fun getInstance(context: Context): AppDatabase {
            // Verifica se a instância já foi criada
            return INSTANCE ?: synchronized(this) {
                // Se a instância ainda não foi criada, cria uma nova
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database" // Nome do banco de dados
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
