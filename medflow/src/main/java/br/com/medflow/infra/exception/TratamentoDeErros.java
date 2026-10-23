package br.com.medflow.infra.exception;

import br.com.medflow.dto.erroDTO.ErroRespostaDTO;
import br.com.medflow.dto.erroDTO.ErroValidacaoDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class TratamentoDeErros {

    @ExceptionHandler(PacienteNaoEncontradoException.class)
    public ResponseEntity<ErroRespostaDTO> tratarPacienteNaoEncontrado(PacienteNaoEncontradoException exception, HttpServletRequest request) {
        var status = HttpStatus.NOT_FOUND;

        var erro = new ErroRespostaDTO(
                status.value(),
                "Recurso não encontrado",
                exception.getMessage(),
                request.getRequestURI(),
                LocalDateTime.now()
        );

        return ResponseEntity
                .status(status)
                .body(erro);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroValidacaoDTO> tratarErroValidacao(MethodArgumentNotValidException  exception, HttpServletRequest request) {
        var status = HttpStatus.BAD_REQUEST;

        var detalhes = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        erro -> erro.getField(),
                        erro -> erro.getDefaultMessage(),
                        (mensagemExistente, novaMensagem) -> mensagemExistente
                ));

        var erro = new ErroValidacaoDTO(
                status.value(),
                "Erro de validação",
                "Um ou mais campos são inválidos",
                request.getRequestURI(),
                LocalDateTime.now(),
                detalhes
        );

        return ResponseEntity
                .status(status)
                .body(erro);
    }

    @ExceptionHandler(EstadoPacienteInvalidoException.class)
    public ResponseEntity<ErroRespostaDTO> tratarEstadoPacienteInvalido(EstadoPacienteInvalidoException exception, HttpServletRequest request) {
        var status = HttpStatus.CONFLICT;

        var erro = new ErroRespostaDTO(
                status.value(),
                "Conflict",
                exception.getMessage(),
                request.getRequestURI(),
                LocalDateTime.now()
        );

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(erro);
    }


    @ExceptionHandler(EmailJaCadastradoException.class)
    public ResponseEntity<ErroRespostaDTO> tratarEmailJaCadastrado(EmailJaCadastradoException exception, HttpServletRequest request) {
        var status = HttpStatus.CONFLICT;

        var erro = new ErroRespostaDTO(
                status.value(),
                "Conflict",
                exception.getMessage(),
                request.getRequestURI(),
                LocalDateTime.now()
        );

        return ResponseEntity
                .status(status)
                .body(erro);
    }

    @ExceptionHandler(MedicoNaoEncontradoException.class)
    public ResponseEntity<ErroRespostaDTO> tratarMedicoNaoEncontrado(MedicoNaoEncontradoException exception, HttpServletRequest request) {
        var status = HttpStatus.NOT_FOUND;

        var erro = new ErroRespostaDTO(
                status.value(),
                "Not Found",
                exception.getMessage(),
                request.getRequestURI(),
                LocalDateTime.now()
        );

        return ResponseEntity.status(status).body(erro);
    }

    @ExceptionHandler(EstadoMedicoInvalidoException.class)
    public ResponseEntity<ErroRespostaDTO> tratarEstadoMedicoInvalido(EstadoMedicoInvalidoException exception, HttpServletRequest request) {
        var status = HttpStatus.CONFLICT;

        var erro = new ErroRespostaDTO(
                status.value(),
                "Conflict",
                exception.getMessage(),
                request.getRequestURI(),
                LocalDateTime.now()
        );

        return ResponseEntity
                .status(status)
                .body(erro);
    }

    @ExceptionHandler(CrmJaCadastradoException.class)
    public ResponseEntity<ErroRespostaDTO> tratarDadosMedicoDuplicados(RuntimeException exception, HttpServletRequest request) {
        var status = HttpStatus.CONFLICT;

        var erro = new ErroRespostaDTO(
                status.value(),
                "Conflict",
                exception.getMessage(),
                request.getRequestURI(),
                LocalDateTime.now()
        );

        return ResponseEntity
                .status(status)
                .body(erro);
    }

    @ExceptionHandler(HorarioConsultaIndisponivelException.class)
    public ResponseEntity<ErroRespostaDTO> tratarHorarioIndisponivel(HorarioConsultaIndisponivelException exception, HttpServletRequest request) {
        var status = HttpStatus.CONFLICT;

        var erro = new ErroRespostaDTO(
                status.value(),
                "Conflict",
                exception.getMessage(),
                request.getRequestURI(),
                LocalDateTime.now()
        );

        return ResponseEntity
                .status(status)
                .body(erro);
    }

    @ExceptionHandler(DataConsultaInvalidaException.class)
    public ResponseEntity<ErroRespostaDTO> tratarDataConsultaInvalida(DataConsultaInvalidaException exception, HttpServletRequest request) {
        var status = HttpStatus.BAD_REQUEST;

        var erro = new ErroRespostaDTO(
                status.value(),
                "Erro de validação",
                exception.getMessage(),
                request.getRequestURI(),
                LocalDateTime.now()
        );

        return ResponseEntity
                .status(status)
                .body(erro);
    }

    @ExceptionHandler(ConsultaNaoEncontradaException.class)
    public ResponseEntity<ErroRespostaDTO> tratarConsultaNaoEncontrada(ConsultaNaoEncontradaException exception, HttpServletRequest request) {
        var status = HttpStatus.NOT_FOUND;

        var erro = new ErroRespostaDTO(
                status.value(),
                "Not Found",
                exception.getMessage(),
                request.getRequestURI(),
                LocalDateTime.now()
        );

        return ResponseEntity
                .status(status)
                .body(erro);
    }

    @ExceptionHandler(EstadoConsultaInvalidoException.class)
    public ResponseEntity<ErroRespostaDTO> tratarEstadoConsultaInvalido(EstadoConsultaInvalidoException exception, HttpServletRequest request) {
        var status = HttpStatus.CONFLICT;

        var erro = new ErroRespostaDTO(
                status.value(),
                "Conflict",
                exception.getMessage(),
                request.getRequestURI(),
                LocalDateTime.now()
        );

        return ResponseEntity
                .status(status)
                .body(erro);
    }

}
