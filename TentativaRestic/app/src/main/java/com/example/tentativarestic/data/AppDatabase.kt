package com.example.tentativarestic.data

// AppDatabase.kt
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tentativarestic.dao.AtividadeDao
import com.example.tentativarestic.dao.CursoDao
import com.example.tentativarestic.dao.ExercicioAbertoDao
import com.example.tentativarestic.dao.ModuloDao
import com.example.tentativarestic.dao.PerguntasQuestionarioDao
import com.example.tentativarestic.dao.PersonDao
import com.example.tentativarestic.dao.PersonCursoDao
import com.example.tentativarestic.dao.ProjetoDao
import com.example.tentativarestic.dao.QuizDao
import com.example.tentativarestic.dao.RespostasQuestionarioDao
import com.example.tentativarestic.dao.RespostasUsuarioQuestionarioDao
import com.example.tentativarestic.dao.UnidadeDao
import com.example.tentativarestic.dao.VideoDao
import com.example.tentativarestic.entities.Atividade
import com.example.tentativarestic.entities.Curso
import com.example.tentativarestic.entities.ExercicioAberto
import com.example.tentativarestic.entities.Modulo
import com.example.tentativarestic.entities.PerguntasQuestionario
import com.example.tentativarestic.entities.Person
import com.example.tentativarestic.entities.PersonCurso
import com.example.tentativarestic.entities.Projeto
import com.example.tentativarestic.entities.Quiz
import com.example.tentativarestic.entities.RespostasQuestionario
import com.example.tentativarestic.entities.RespostasUsuarioQuestionario
import com.example.tentativarestic.entities.Unidade
import com.example.tentativarestic.entities.Video

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
}
