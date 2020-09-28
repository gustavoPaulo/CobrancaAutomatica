package com.CobrancaAutomatica.controller;

import com.CobrancaAutomatica.controller.SemPararController;
import com.CobrancaAutomatica.model.T_INFO_SEMPARAR;
import com.CobrancaAutomatica.repository.SemPararRepository;
import com.CobrancaAutomatica.service.EnvioEmailService;
import com.CobrancaAutomatica.service.ReportUtil;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@SuppressWarnings("unused")
@Controller
public class SemPararController {
	
	private static final String INDEX_SEMPARAR = "IndexSemParar";

	private static final String CADASTRO_SEMPARAR = "cadastro/cadastroSemParar";

	private static final String DETALHES_SEMPARAR = "cadastro/detalhesSemParar";

	@Autowired
	private SemPararRepository sempararRepository;

	@Autowired
	private ReportUtil reportUtil;

	@Autowired
	private EnvioEmailService envioEmailService;

	@RequestMapping({ "/CobrancaAutomatica" })
	public ModelAndView index() {
		ModelAndView mv = new ModelAndView("IndexSemParar");
		String[] cliente = this.sempararRepository.findPessoaByClienteTodos();
		mv.addObject("cliente", cliente);
		mv.addObject("semparar",
				this.sempararRepository.findAll((Pageable) PageRequest.of(0, 10, Sort.by(new String[] { "UNIDADE" }))));
		return mv;
	}

	@RequestMapping(method = { RequestMethod.GET }, value = { "/listaSemParar" })
	public ModelAndView semparar() {
		ModelAndView mv = new ModelAndView("IndexSemParar");
		mv.addObject("semparar", this.sempararRepository.findAll());
		String[] cliente = this.sempararRepository.findPessoaByClienteTodos();
		mv.addObject("cliente", cliente);
		mv.addObject("semparar",
				this.sempararRepository.findAll((Pageable) PageRequest.of(0, 10, Sort.by(new String[] { "UNIDADE" }))));
		return mv;
	}

	@RequestMapping({ "/cadastroSemParar" })
	public ModelAndView cadastroSemparar() {
		ModelAndView mv = new ModelAndView("cadastro/cadastroSemParar");
		mv.addObject("sempararObj", new T_INFO_SEMPARAR());
		return mv;
	}

	@PostMapping({ "/novoSemParar" })
	public ModelAndView criar(@Valid T_INFO_SEMPARAR sempararModel, BindingResult bindingResult) throws IOException {
		if (bindingResult.hasErrors()) {
			ModelAndView modelAndView = new ModelAndView("cadastro/cadastroSemParar");
			modelAndView.addObject("sempararObj", sempararModel);
			List<String> msg = new ArrayList<>();
			for (ObjectError objectError : bindingResult.getAllErrors())
				msg.add(objectError.getDefaultMessage());
			modelAndView.addObject("msgErro", msg);
			return modelAndView;
		}
		this.sempararRepository.save(sempararModel);
		ModelAndView mv = new ModelAndView("cadastro/cadastroSemParar");
		mv.addObject("sempararObj", new T_INFO_SEMPARAR());
		mv.addObject("mensagem", "Hash salvo com sucesso!");
		return mv;
	}

	@GetMapping({ "/excluirSemparar/{idSemparar}" })
	public ModelAndView excluir(@PathVariable("idSemparar") Long idSemparar) {
		this.sempararRepository.deleteById(idSemparar);
		ModelAndView mv = new ModelAndView("IndexSemParar");
		mv.addObject("semparar", this.sempararRepository.findAll());
		mv.addObject("mensagem", "Hash excluido com sucesso!");
		String[] cliente = this.sempararRepository.findPessoaByClienteTodos();
		mv.addObject("cliente", cliente);
		mv.addObject("semparar",
				this.sempararRepository.findAll((Pageable) PageRequest.of(0, 10, Sort.by(new String[] { "UNIDADE" }))));
		return mv;
	}

	@GetMapping({ "/editarSemparar/{idSemparar}" })
	public ModelAndView editar(@PathVariable("idSemparar") Long idSemparar) {
		ModelAndView mv = new ModelAndView("cadastro/cadastroSemParar");
		Optional<T_INFO_SEMPARAR> semparar = this.sempararRepository.findById(idSemparar);
		mv.addObject("sempararObj", semparar.get());
		return mv;
	}

	@GetMapping({ "/detalhesSemparar/{idSemparar}" })
	public ModelAndView telefone(@PathVariable("idSemparar") Long idSemparar) {
		Optional<T_INFO_SEMPARAR> semparar = this.sempararRepository.findById(idSemparar);
		ModelAndView mv = new ModelAndView("cadastro/detalhesSemParar");
		mv.addObject("sempararObj", semparar.get());
		return mv;
	}

	@PostMapping({ "/pesquisarSemparar" })
	public ModelAndView pesquisar(@RequestParam("nomePesquisaSemparar") String nomePesquisaSemparar,
			@RequestParam("EMPRESA") String EMPRESA, @RequestParam("DataInicio") String DataInicio,
			@RequestParam("DataFim") String DataFim,
			@PageableDefault(size = 10, sort = { "UNIDADE" }) Pageable pageable) throws ParseException {
		Page<T_INFO_SEMPARAR> sempararModel = null;
		if (!EMPRESA.isEmpty() && !nomePesquisaSemparar.isEmpty() && DataInicio.isEmpty() && DataFim.isEmpty()) {
			sempararModel = this.sempararRepository.findPessoaByUnidadeClientePage(nomePesquisaSemparar, EMPRESA,
					pageable);
		} else if (EMPRESA.isEmpty() && nomePesquisaSemparar.isEmpty() && DataInicio.isEmpty() && DataFim.isEmpty()) {
			sempararModel = this.sempararRepository.findPessoaByClientePage(EMPRESA, pageable);
		} else if (!nomePesquisaSemparar.isEmpty() && EMPRESA.isEmpty() && DataInicio.isEmpty() && DataFim.isEmpty()) {
			sempararModel = this.sempararRepository.findPessoaByUnidadePage(nomePesquisaSemparar, pageable);
		} else if ((!DataInicio.isEmpty() && !DataFim.isEmpty() && !EMPRESA.isEmpty() && !EMPRESA.equals("Todos"))
				|| (!DataInicio.isEmpty() && !DataFim.isEmpty() && EMPRESA.isEmpty())) {
			Date DataInicioFormatada = (new SimpleDateFormat("dd/MM/yyyy")).parse(DataInicio);
			Date DataFimFormatada = (new SimpleDateFormat("dd/MM/yyyy")).parse(DataFim);
			sempararModel = this.sempararRepository.findPessoaByDataPage(DataInicioFormatada, DataFimFormatada, EMPRESA,
					pageable);
		} else if (EMPRESA.equals("Todos") && !DataInicio.isEmpty() && !DataFim.isEmpty()) {
			Date DataInicioFormatada = (new SimpleDateFormat("dd/MM/yyyy")).parse(DataInicio);
			Date DataFimFormatada = (new SimpleDateFormat("dd/MM/yyyy")).parse(DataFim);
			sempararModel = this.sempararRepository.findPessoaByTodosCliente(DataInicioFormatada, DataFimFormatada,
					pageable);
		}
		ModelAndView mv = new ModelAndView("IndexSemParar");
		mv.addObject("semparar", sempararModel);
		mv.addObject("nomePesquisaSemparar", nomePesquisaSemparar);
		mv.addObject("EMPRESA", EMPRESA);
		mv.addObject("DataInicio", DataInicio);
		mv.addObject("DataFim", DataFim);
		String[] cliente = this.sempararRepository.findPessoaByClienteTodos();
		mv.addObject("cliente", cliente);
		return mv;
	}

	@GetMapping({ "/pesquisarSemparar" })
	public void imprimePdf(@RequestParam("nomePesquisaSemparar") String nomePesquisaSemparar,
			@RequestParam("EMPRESA") String EMPRESA, @RequestParam("DataInicio") String DataInicio,
			@RequestParam("DataFim") String DataFim, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List<T_INFO_SEMPARAR> sempararModel = new ArrayList<>();
		if (!EMPRESA.isEmpty() && !nomePesquisaSemparar.isEmpty() && DataInicio.isEmpty() && DataFim.isEmpty()) {
			sempararModel = this.sempararRepository.findPessoaByUnidadeCliente(nomePesquisaSemparar, EMPRESA);
		} else if (EMPRESA != null && nomePesquisaSemparar.isEmpty() && DataInicio.isEmpty() && DataFim.isEmpty()) {
			sempararModel = this.sempararRepository.findPessoaByCliente(EMPRESA);
		} else if (!nomePesquisaSemparar.isEmpty() && EMPRESA.isEmpty() && DataInicio.isEmpty() && DataFim.isEmpty()) {
			sempararModel = this.sempararRepository.findPessoaByUnidade(nomePesquisaSemparar);
		} else if ((!DataInicio.isEmpty() && !DataFim.isEmpty() && !EMPRESA.isEmpty() && !EMPRESA.equals("Todos"))
				|| (!DataInicio.isEmpty() && !DataFim.isEmpty() && EMPRESA.isEmpty())) {
			Date DataInicioFormatada = (new SimpleDateFormat("dd/MM/yyyy")).parse(DataInicio);
			Date DataFimFormatada = (new SimpleDateFormat("dd/MM/yyyy")).parse(DataFim);
			sempararModel = this.sempararRepository.findPessoaByData(DataInicioFormatada, DataFimFormatada, EMPRESA);
		} else if (EMPRESA.equals("Todos") && !DataInicio.isEmpty() && !DataFim.isEmpty()) {
			Date DataInicioFormatada = (new SimpleDateFormat("dd/MM/yyyy")).parse(DataInicio);
			Date DataFimFormatada = (new SimpleDateFormat("dd/MM/yyyy")).parse(DataFim);
			sempararModel = this.sempararRepository.findPessoaByTodosClienteComData(DataInicioFormatada,
					DataFimFormatada);
		}
		byte[] pdf = this.reportUtil.geraRelatorio(sempararModel, "T_INFO_SEMPARAR", request.getServletContext());
		response.setContentLength(pdf.length);
		response.setContentType("application/octet-stream");
		String headerKey = "Content-Disposition";
		String headerValue = String.format("attachment; filename=\"%s\"", new Object[] { "relatorio.pdf" });
		response.setHeader(headerKey, headerValue);
		response.getOutputStream().write(pdf);
	}

	@GetMapping({ "/pesquisarEmailSemparar" })
	public ModelAndView enviarPdfEmail(@RequestParam("nomePesquisaSemparar") String nomePesquisaSemparar,
			@RequestParam("EMPRESA") String EMPRESA, @RequestParam("DataInicio") String DataInicio,
			@RequestParam("DataFim") String DataFim, HttpServletRequest request, HttpServletResponse response,
			String enviarPara) throws Exception {
		List<T_INFO_SEMPARAR> sempararModel = new ArrayList<>();
		if (!EMPRESA.isEmpty() && !nomePesquisaSemparar.isEmpty() && DataInicio.isEmpty() && DataFim.isEmpty()) {
			sempararModel = this.sempararRepository.findPessoaByUnidadeCliente(nomePesquisaSemparar, EMPRESA);
		} else if (EMPRESA != null && nomePesquisaSemparar.isEmpty() && DataInicio.isEmpty() && DataFim.isEmpty()) {
			sempararModel = this.sempararRepository.findPessoaByCliente(EMPRESA);
		} else if (!nomePesquisaSemparar.isEmpty() && EMPRESA.isEmpty() && DataInicio.isEmpty() && DataFim.isEmpty()) {
			sempararModel = this.sempararRepository.findPessoaByUnidade(nomePesquisaSemparar);
		} else if ((!DataInicio.isEmpty() && !DataFim.isEmpty() && !EMPRESA.isEmpty() && !EMPRESA.equals("Todos"))
				|| (!DataInicio.isEmpty() && !DataFim.isEmpty() && EMPRESA.isEmpty())) {
			Date DataInicioFormatada = (new SimpleDateFormat("dd/MM/yyyy")).parse(DataInicio);
			Date DataFimFormatada = (new SimpleDateFormat("dd/MM/yyyy")).parse(DataFim);
			sempararModel = this.sempararRepository.findPessoaByData(DataInicioFormatada, DataFimFormatada, EMPRESA);
		} else if (EMPRESA.equals("Todos") && !DataInicio.isEmpty() && !DataFim.isEmpty()) {
			Date DataInicioFormatada = (new SimpleDateFormat("dd/MM/yyyy")).parse(DataInicio);
			Date DataFimFormatada = (new SimpleDateFormat("dd/MM/yyyy")).parse(DataFim);
			sempararModel = this.sempararRepository.findPessoaByTodosClienteComData(DataInicioFormatada,
					DataFimFormatada);
		}
		byte[] pdf = this.reportUtil.geraRelatorio(sempararModel, "T_INFO_SEMPARAR", request.getServletContext());
		this.envioEmailService.enviaRelatorioEmail(enviarPara, pdf);
		ModelAndView mv = new ModelAndView("IndexSemParar");
		mv.addObject("mensagem", "E-mail enviado com sucesso!");
		String[] cliente = this.sempararRepository.findPessoaByClienteTodos();
		mv.addObject("cliente", cliente);
		mv.addObject("EMPRESA", EMPRESA);
		mv.addObject("semparar",
				this.sempararRepository.findAll((Pageable) PageRequest.of(0, 10, Sort.by(new String[] { "UNIDADE" }))));
		return mv;
	}

	@GetMapping({ "/hashPaginaSemparar" })
	public ModelAndView carregaPessoaPorPagina(@PageableDefault(size = 10) Pageable pageable, ModelAndView mv,
			@RequestParam("nomePesquisaSemparar") String nomePesquisaSemparar, @RequestParam("EMPRESA") String EMPRESA,
			@RequestParam("DataInicio") String DataInicio, @RequestParam("DataFim") String DataFim)
			throws ParseException {
		Page<T_INFO_SEMPARAR> pageSempararModel = null;
		if (!EMPRESA.isEmpty() && !nomePesquisaSemparar.isEmpty() && DataInicio.isEmpty() && DataFim.isEmpty()) {
			pageSempararModel = this.sempararRepository.findPessoaByUnidadeClientePage(nomePesquisaSemparar, EMPRESA,
					pageable);
		} else if (EMPRESA.isEmpty() && nomePesquisaSemparar.isEmpty() && DataInicio.isEmpty() && DataFim.isEmpty()) {
			pageSempararModel = this.sempararRepository.findPessoaByClientePage(EMPRESA, pageable);
		} else if (!nomePesquisaSemparar.isEmpty() && EMPRESA.isEmpty() && DataInicio.isEmpty() && DataFim.isEmpty()) {
			pageSempararModel = this.sempararRepository.findPessoaByUnidadePage(nomePesquisaSemparar, pageable);
		} else if ((!DataInicio.isEmpty() && !DataFim.isEmpty() && !EMPRESA.isEmpty() && !EMPRESA.equals("Todos"))
				|| (!DataInicio.isEmpty() && !DataFim.isEmpty() && EMPRESA.isEmpty())) {
			Date DataInicioFormatada = (new SimpleDateFormat("dd/MM/yyyy")).parse(DataInicio);
			Date DataFimFormatada = (new SimpleDateFormat("dd/MM/yyyy")).parse(DataFim);
			pageSempararModel = this.sempararRepository.findPessoaByDataPage(DataInicioFormatada, DataFimFormatada,
					EMPRESA, pageable);
		} else if (EMPRESA.equals("Todos") && !DataInicio.isEmpty() && !DataFim.isEmpty()) {
			Date DataInicioFormatada = (new SimpleDateFormat("dd/MM/yyyy")).parse(DataInicio);
			Date DataFimFormatada = (new SimpleDateFormat("dd/MM/yyyy")).parse(DataFim);
			pageSempararModel = this.sempararRepository.findPessoaByTodosCliente(DataInicioFormatada, DataFimFormatada,
					pageable);
		}
		mv.addObject("nomePesquisaSemparar", nomePesquisaSemparar);
		mv.addObject("semparar", pageSempararModel);
		mv.addObject("EMPRESA", EMPRESA);
		mv.addObject("DataInicio", DataInicio);
		mv.addObject("DataFim", DataFim);
		String[] cliente = this.sempararRepository.findPessoaByClienteTodos();
		mv.addObject("cliente", cliente);
		mv.setViewName("IndexSemParar");
		return mv;
	}
}
