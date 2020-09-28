package com.CobrancaAutomatica.controller;

import com.CobrancaAutomatica.controller.ConectCarController;
import com.CobrancaAutomatica.model.T_INFO_CONECTCAR;
import com.CobrancaAutomatica.repository.ConectCarRepository;
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
public class ConectCarController {
	
	private static final String INDEX_CONECTCAR = "IndexConectCar";

	private static final String CADASTRO_CONECTCAR = "cadastro/cadastroConectCar";

	private static final String DETALHES_CONECTCAR = "cadastro/detalhesConectCar";

	@Autowired
	private ConectCarRepository conectcarRepository;

	@Autowired
	private ReportUtil reportUtil;

	@Autowired
	private EnvioEmailService envioEmailService;

	
	@RequestMapping({ "/ConectCar" })
	public ModelAndView index() {
		ModelAndView mv = new ModelAndView("IndexConectCar");
		String[] cliente = this.conectcarRepository.findPessoaByClienteTodos();
		mv.addObject("clienteConectcar", cliente);
		mv.addObject("conectcar", this.conectcarRepository
				.findAll((Pageable) PageRequest.of(0, 10, Sort.by(new String[] { "UNIDADE" }))));
		return mv;
	}

	@RequestMapping(method = { RequestMethod.GET }, value = { "/listaConectCar" })
	public ModelAndView conectcar() {
		ModelAndView mv = new ModelAndView("IndexConectCar");
		mv.addObject("conectcar", this.conectcarRepository.findAll());
		String[] cliente = this.conectcarRepository.findPessoaByClienteTodos();
		mv.addObject("clienteConectcar", cliente);
		mv.addObject("conectcar", this.conectcarRepository
				.findAll((Pageable) PageRequest.of(0, 10, Sort.by(new String[] { "UNIDADE" }))));
		return mv;
	}

	@RequestMapping({ "/cadastroConectcar" })
	public ModelAndView cadastroConectcar() {
		ModelAndView mv = new ModelAndView("cadastro/cadastroConectCar");
		mv.addObject("conectcarObj", new T_INFO_CONECTCAR());
		return mv;
	}

	@PostMapping({ "/novoConectCar" })
	public ModelAndView criar(@Valid T_INFO_CONECTCAR conectcarModel, BindingResult bindingResult) throws IOException {
		if (bindingResult.hasErrors()) {
			ModelAndView modelAndView = new ModelAndView("cadastro/cadastroConectCar");
			modelAndView.addObject("conectcarObj", conectcarModel);
			List<String> msg = new ArrayList<>();
			for (ObjectError objectError : bindingResult.getAllErrors())
				msg.add(objectError.getDefaultMessage());
			modelAndView.addObject("msgErro", msg);
			return modelAndView;
		}
		this.conectcarRepository.save(conectcarModel);
		ModelAndView mv = new ModelAndView("cadastro/cadastroConectCar");
		mv.addObject("conectcarObj", new T_INFO_CONECTCAR());
		mv.addObject("mensagem", "Hash salvo com sucesso!");
		return mv;
	}

	@GetMapping({ "/excluirConectcar/{idConectcar}" })
	public ModelAndView excluir(@PathVariable("idConectcar") Long idConectcar) {
		this.conectcarRepository.deleteById(idConectcar);
		ModelAndView mv = new ModelAndView("IndexConectCar");
		mv.addObject("conectcar", this.conectcarRepository.findAll());
		mv.addObject("mensagem", "Hash excluido com sucesso!");
		String[] cliente = this.conectcarRepository.findPessoaByClienteTodos();
		mv.addObject("clienteConectcar", cliente);
		mv.addObject("conectcar", this.conectcarRepository
				.findAll((Pageable) PageRequest.of(0, 10, Sort.by(new String[] { "UNIDADE" }))));
		return mv;
	}

	@GetMapping({ "/editarConectcar/{idConectcar}" })
	public ModelAndView editar(@PathVariable("idConectcar") Long idConectcar) {
		ModelAndView mv = new ModelAndView("cadastro/cadastroConectCar");
		Optional<T_INFO_CONECTCAR> conectcar = this.conectcarRepository.findById(idConectcar);
		mv.addObject("conectcarObj", conectcar.get());
		return mv;
	}

	@GetMapping({ "/detalhesConectcar/{idConectcar}" })
	public ModelAndView telefone(@PathVariable("idConectcar") Long idConectcar) {
		Optional<T_INFO_CONECTCAR> conectcar = this.conectcarRepository.findById(idConectcar);
		ModelAndView mv = new ModelAndView("cadastro/detalhesConectCar");
		mv.addObject("conectcarObj", conectcar.get());
		return mv;
	}

	@PostMapping({ "/pesquisarConectcar" })
	public ModelAndView pesquisar(@RequestParam("nomePesquisaConectcar") String nomePesquisaConectcar,
			@RequestParam("NM_EMPRESA") String NM_EMPRESA, @RequestParam("DataInicio") String DataInicio,
			@RequestParam("DataFim") String DataFim,
			@PageableDefault(size = 10, sort = { "UNIDADE" }) Pageable pageable) throws Exception {
		Page<T_INFO_CONECTCAR> conectcarModel = null;
		if (!NM_EMPRESA.isEmpty() && !nomePesquisaConectcar.isEmpty()) {
			conectcarModel = this.conectcarRepository.findPessoaByUnidadeClientePage(nomePesquisaConectcar, NM_EMPRESA,
					pageable);
		} else if (NM_EMPRESA.isEmpty() && nomePesquisaConectcar.isEmpty()) {
			conectcarModel = this.conectcarRepository.findPessoaByClientePage(NM_EMPRESA, pageable);
		} else if (!nomePesquisaConectcar.isEmpty() && NM_EMPRESA.isEmpty()) {
			conectcarModel = this.conectcarRepository.findPessoaByUnidadePage(nomePesquisaConectcar, pageable);
		} else if ((!DataInicio.isEmpty() && !DataFim.isEmpty() && !NM_EMPRESA.isEmpty() && !NM_EMPRESA.equals("Todos"))
				|| (!DataInicio.isEmpty() && !DataFim.isEmpty() && NM_EMPRESA.isEmpty())) {
			Date DataInicioFormatada = (new SimpleDateFormat("dd/MM/yyyy")).parse(DataInicio);
			Date DataFimFormatada = (new SimpleDateFormat("dd/MM/yyyy")).parse(DataFim);
			conectcarModel = this.conectcarRepository.findPessoaByDataPage(DataInicioFormatada, DataFimFormatada,
					NM_EMPRESA, pageable);
		} else if (NM_EMPRESA.equals("Todos") && !DataInicio.isEmpty() && !DataFim.isEmpty()) {
			Date DataInicioFormatada = (new SimpleDateFormat("dd/MM/yyyy")).parse(DataInicio);
			Date DataFimFormatada = (new SimpleDateFormat("dd/MM/yyyy")).parse(DataFim);
			conectcarModel = this.conectcarRepository.findPessoaByTodosCliente(DataInicioFormatada, DataFimFormatada,
					pageable);
		}
		ModelAndView mv = new ModelAndView("IndexConectCar");
		mv.addObject("conectcar", conectcarModel);
		mv.addObject("nomePesquisaConectcar", nomePesquisaConectcar);
		mv.addObject("NM_EMPRESA", NM_EMPRESA);
		mv.addObject("DataInicio", DataInicio);
		mv.addObject("DataFim", DataFim);
		
		String[] cliente = this.conectcarRepository.findPessoaByClienteTodos();
		mv.addObject("clienteConectcar", cliente);
		
		return mv;
	}

	@GetMapping({ "/pesquisarConectcar" })
	public void imprimePdf(@RequestParam("nomePesquisaConectcar") String nomePesquisaConectcar,
			@RequestParam("NM_EMPRESA") String NM_EMPRESA, @RequestParam("DataInicio") String DataInicio,
			@RequestParam("DataFim") String DataFim, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List<T_INFO_CONECTCAR> conectcarModel = new ArrayList<>();
		if (!NM_EMPRESA.isEmpty() && !nomePesquisaConectcar.isEmpty()) {
			conectcarModel = this.conectcarRepository.findPessoaByUnidadeCliente(nomePesquisaConectcar, NM_EMPRESA);
		} else if (NM_EMPRESA.isEmpty() && nomePesquisaConectcar.isEmpty()) {
			conectcarModel = this.conectcarRepository.findPessoaByCliente(NM_EMPRESA);
		} else if (!nomePesquisaConectcar.isEmpty() && NM_EMPRESA.isEmpty()) {
			conectcarModel = this.conectcarRepository.findPessoaByUnidade(nomePesquisaConectcar);
		} else if ((!DataInicio.isEmpty() && !DataFim.isEmpty() && !NM_EMPRESA.isEmpty() && !NM_EMPRESA.equals("Todos"))
				|| (!DataInicio.isEmpty() && !DataFim.isEmpty() && NM_EMPRESA.isEmpty())) {
			Date DataInicioFormatada = (new SimpleDateFormat("dd/MM/yyyy")).parse(DataInicio);
			Date DataFimFormatada = (new SimpleDateFormat("dd/MM/yyyy")).parse(DataFim);
			conectcarModel = this.conectcarRepository.findPessoaByData(DataInicioFormatada, DataFimFormatada,
					NM_EMPRESA);
		} else if (NM_EMPRESA.equals("Todos") && !DataInicio.isEmpty() && !DataFim.isEmpty()) {
			Date DataInicioFormatada = (new SimpleDateFormat("dd/MM/yyyy")).parse(DataInicio);
			Date DataFimFormatada = (new SimpleDateFormat("dd/MM/yyyy")).parse(DataFim);
			conectcarModel = this.conectcarRepository.findPessoaByTodosClienteComData(DataInicioFormatada,
					DataFimFormatada);
		}
		byte[] pdf = this.reportUtil.geraRelatorio(conectcarModel, "T_INFO_CONECTCAR", request.getServletContext());
		response.setContentLength(pdf.length);
		response.setContentType("application/octet-stream");
		String headerKey = "Content-Disposition";
		String headerValue = String.format("attachment; filename=\"%s\"", new Object[] { "relatorio.pdf" });
		response.setHeader(headerKey, headerValue);
		response.getOutputStream().write(pdf);
	}

	@GetMapping({ "/pesquisarEmailConectcar" })
	public ModelAndView enviarPdfEmail(@RequestParam("nomePesquisaConectcar") String nomePesquisaConectcar,
			@RequestParam("NM_EMPRESA") String NM_EMPRESA, @RequestParam("DataInicio") String DataInicio,
			@RequestParam("DataFim") String DataFim, HttpServletRequest request, HttpServletResponse response,
			String enviarParaConectcar) throws Exception {
		List<T_INFO_CONECTCAR> conectcarModel = new ArrayList<>();
		if (!NM_EMPRESA.isEmpty() && !nomePesquisaConectcar.isEmpty()) {
			conectcarModel = this.conectcarRepository.findPessoaByUnidadeCliente(nomePesquisaConectcar, NM_EMPRESA);
		} else if (NM_EMPRESA.isEmpty() && nomePesquisaConectcar.isEmpty()) {
			conectcarModel = this.conectcarRepository.findPessoaByCliente(NM_EMPRESA);
		} else if (!nomePesquisaConectcar.isEmpty() && NM_EMPRESA.isEmpty()) {
			conectcarModel = this.conectcarRepository.findPessoaByUnidade(nomePesquisaConectcar);
		} else if ((!DataInicio.isEmpty() && !DataFim.isEmpty() && !NM_EMPRESA.isEmpty() && !NM_EMPRESA.equals("Todos"))
				|| (!DataInicio.isEmpty() && !DataFim.isEmpty() && NM_EMPRESA.isEmpty())) {
			Date DataInicioFormatada = (new SimpleDateFormat("dd/MM/yyyy")).parse(DataInicio);
			Date DataFimFormatada = (new SimpleDateFormat("dd/MM/yyyy")).parse(DataFim);
			conectcarModel = this.conectcarRepository.findPessoaByData(DataInicioFormatada, DataFimFormatada,
					NM_EMPRESA);
		} else if (NM_EMPRESA.equals("Todos") && !DataInicio.isEmpty() && !DataFim.isEmpty()) {
			Date DataInicioFormatada = (new SimpleDateFormat("dd/MM/yyyy")).parse(DataInicio);
			Date DataFimFormatada = (new SimpleDateFormat("dd/MM/yyyy")).parse(DataFim);
			conectcarModel = this.conectcarRepository.findPessoaByTodosClienteComData(DataInicioFormatada,
					DataFimFormatada);
		}
		
		byte[] pdf = this.reportUtil.geraRelatorio(conectcarModel, "T_INFO_CONECTCAR", request.getServletContext());
		
		this.envioEmailService.enviaRelatorioEmailConectcar(enviarParaConectcar, pdf);
		
		ModelAndView mv = new ModelAndView("IndexConectCar");
		
		mv.addObject("mensagem", "E-mail enviado com sucesso!");
		
		String[] cliente = this.conectcarRepository.findPessoaByClienteTodos();
		mv.addObject("cliente", cliente);
		
		mv.addObject("conectcar", this.conectcarRepository
				.findAll((Pageable) PageRequest.of(0, 10, Sort.by(new String[] { "UNIDADE" }))));
		
		return mv;
	}

	@GetMapping({ "/hashPaginaConectcar" })
	public ModelAndView carregaPessoaPorPagina(@PageableDefault(size = 10) Pageable pageable, ModelAndView mv,
			@RequestParam("nomePesquisaConectcar") String nomePesquisaConectcar,
			@RequestParam("NM_EMPRESA") String NM_EMPRESA, @RequestParam("DataInicio") String DataInicio,
			@RequestParam("DataFim") String DataFim) throws ParseException {
		Page<T_INFO_CONECTCAR> pageConectcarModel = null;
		if (!NM_EMPRESA.isEmpty() && !nomePesquisaConectcar.isEmpty() && DataInicio.isEmpty() && DataFim.isEmpty()) {
			pageConectcarModel = this.conectcarRepository.findPessoaByUnidadeClientePage(nomePesquisaConectcar,
					NM_EMPRESA, pageable);
		} else if (NM_EMPRESA.isEmpty() && nomePesquisaConectcar.isEmpty() && DataInicio.isEmpty()
				&& DataFim.isEmpty()) {
			pageConectcarModel = this.conectcarRepository.findPessoaByClientePage(NM_EMPRESA, pageable);
		} else if (!nomePesquisaConectcar.isEmpty() && NM_EMPRESA.isEmpty() && DataInicio.isEmpty()
				&& DataFim.isEmpty()) {
			pageConectcarModel = this.conectcarRepository.findPessoaByUnidadePage(nomePesquisaConectcar, pageable);
		} else if ((!DataInicio.isEmpty() && !DataFim.isEmpty() && !NM_EMPRESA.isEmpty() && !NM_EMPRESA.equals("Todos"))
				|| (!DataInicio.isEmpty() && !DataFim.isEmpty() && NM_EMPRESA.isEmpty())) {
			Date DataInicioFormatada = (new SimpleDateFormat("dd/MM/yyyy")).parse(DataInicio);
			Date DataFimFormatada = (new SimpleDateFormat("dd/MM/yyyy")).parse(DataFim);
			pageConectcarModel = this.conectcarRepository.findPessoaByDataPage(DataInicioFormatada, DataFimFormatada,
					NM_EMPRESA, pageable);
		} else if (NM_EMPRESA.equals("Todos") && !DataInicio.isEmpty() && !DataFim.isEmpty()) {
			Date DataInicioFormatada = (new SimpleDateFormat("dd/MM/yyyy")).parse(DataInicio);
			Date DataFimFormatada = (new SimpleDateFormat("dd/MM/yyyy")).parse(DataFim);
			pageConectcarModel = this.conectcarRepository.findPessoaByTodosCliente(DataInicioFormatada,
					DataFimFormatada, pageable);
		}
		
		mv.addObject("nomePesquisaConectcar", nomePesquisaConectcar);
		mv.addObject("conectcar", pageConectcarModel);
		mv.addObject("NM_EMPRESA", NM_EMPRESA);
		mv.addObject("DataInicio", DataInicio);
		mv.addObject("DataFim", DataFim);
		
		String[] cliente = this.conectcarRepository.findPessoaByClienteTodos();
		mv.addObject("cliente", cliente);
		mv.setViewName("IndexConectCar");
		
		return mv;
	}
}
