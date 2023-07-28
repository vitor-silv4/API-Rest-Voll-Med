package med.voll.api.domain.consulta;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DadosCacelamentoConsulta(

        @NotNull
        Long idConsulta,
        @NotNull
        MotivoCancelamento motivoCancelamento) {
}
