package med.voll.api.domain.consulta;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.medico.Especialidade;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record DadosAgendamentosConsulta(
        Long idMedico,
        @NotNull
        Long idPaciente,
        @NotNull
        @Future //Anotação para que esse atributo só pode aceitar datas futuras
        LocalDateTime data,

        Especialidade especialidade
) {
}
