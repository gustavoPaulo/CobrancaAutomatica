$('#confirmacaoExclusaoModalSemparar').on('show.bs.modal', function(event){
	
	var button = $(event.relatedTarget);
	
	var idSemparar = button.data('id');
	var unidadeSemparar = button.data('unidade');
	
	var modal = $(this);
	var form = modal.find('form');
	var action = form.data('url-base');
	
	if(!action.endsWith('/')){
		
		action += '/';
	}
	
	form.attr('action', action + idSemparar);
	
	modal.find('.modal-body span').html('Você deseja mesmo excluir o hash da unidade <strong>' + unidadeSemparar + '</strong>? <br>'
										+ 'Todas as informações serão perdidas.');
	
});

$(function(){
	
	$('[rel="tooltip"]').tooltip();
	
});

function validarData() {

	if(document.getElementById("DataInicio").value == ''){
		$('#DataInicio').val('01/01/1900');	
	
	  if(document.getElementById("DataFim").value == ''){
		  $('#DataFim').val('01/01/2050');
	  }
		  return false;
	}
	
	return true;
}