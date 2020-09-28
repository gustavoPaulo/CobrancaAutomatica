function setaListaEmail(){

	var listaEmail = document.getElementById('listaEmailModal').value;

	document.getElementById('enviarPara').value = listaEmail;
	
	
	document.getElementById('pesquisarSemparar').action = "/CobrancaAutomatica/pesquisarEmailSemparar";
	document.getElementById('pesquisarSemparar').method = 'get';
}