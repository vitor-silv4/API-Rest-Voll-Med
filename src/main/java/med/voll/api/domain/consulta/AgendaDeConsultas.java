package med.voll.api.domain.consulta;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.validacoes.ValidadorAgendamentoDeConsulta;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AgendaDeConsultas {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private List<ValidadorAgendamentoDeConsulta> validadores;

    public DadosDetalhamentoConsuta agendar(DadosAgendamentosConsulta dados){

        if(!pacienteRepository.existsById(dados.idPaciente())){
            throw new ValidacaoException("Id do paciente informado não existe!");
        }
        if(dados.idMedico() != null && !medicoRepository.existsById(dados.idMedico())){
            throw new ValidacaoException("Id do medico informado não existe!");
        }

        validadores.forEach(v -> v.validar(dados));

        var paciente = pacienteRepository.getReferenceById(dados.idPaciente());
        var medico = escolherMedico(dados);
        if(medico == null){
            throw new ValidacaoException("Não existe médico disponível nessa data");
        }
        var consulta = new Consulta(null, medico, paciente, dados.data());

        consultaRepository.save(consulta);

        return new DadosDetalhamentoConsuta(consulta);
    }

    public void cancelar(DadosCacelamentoConsulta dados) {

    Consulta consulta = consultaRepository.findById(dados.idConsulta()).get();
    var momentoDoCancelamento = LocalDateTime.now();
    var momentoDaConsulta = consulta.getData();
    var diferencaDeHorasParaConsulta = Duration.between(momentoDoCancelamento, momentoDaConsulta).toHours();

    if(consulta == null){
        throw new ValidacaoException("Deve ser informado um id de consulta válido!");
    }
    if(diferencaDeHorasParaConsulta < 24){
        throw new ValidacaoException("A consulta não pode ser cancelada, essa ação só pode ser feita com no mínimo 24 horas de antecedência");
    }

    consultaRepository.deleteById(dados.idConsulta());

    }

    private Medico escolherMedico(DadosAgendamentosConsulta dados) {
        if(dados.idMedico() != null) {
            return medicoRepository.getReferenceById(dados.idMedico());
        }

       if(dados.especialidade() == null){
           throw new ValidacaoException("Especialicade é obrigatória quando o médico não for escolhido ");
       }

       return medicoRepository.escolherMedicoAleatorioLivreNaData(dados.especialidade(), dados.data());
    }

}
