function setaListaEmailConectcar(){

	var listaEmailConectcar = document.getElementById('listaEmailModalConectcar').value;

	document.getElementById('enviarParaConectcar').value = listaEmailConectcar;
	
	
	document.getElementById('pesquisarConectcar').action = "/CobrancaAutomatica/pesquisarEmailConectcar";
	document.getElementById('pesquisarConectcar').method = 'get';
}