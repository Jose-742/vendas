
//***** */ocutando botao da pagina de vendas
window.onload = function(){//escondendo o buscar por data e Nome
    $("#dataPesquisa").hide();
    $("#nomePesquisa").hide();
    $('#formEndereco').hide();//form Cep
    $("#loader-img").hide();//classe loader carregando
}

$('#mostrarData').on("click", function(){//mostranto o buscar por data
	ocultaPesquisa("#dataPesquisa")
});

$('#mostrarNome').on("click", function(){//mostranto o buscar por Nome
	ocultaPesquisa("#nomePesquisa")
});

//Oculta input do buscar por data e nome
let verificarClick = true;
function ocultaPesquisa(id){
	if(verificarClick){
		$(id).show();
		verificarClick = false;
	}else{
		$(id).hide();
		verificarClick = true;
	}	
}


//pesquisa da pagina poduto/list
$('#pesquisa').autocomplete({
	source: function(request, response){
		$.ajax({
			method: "GET",
			url: "/produtos/descricao",
			data: {
				termo: request.term
			},
			success: function(result){
				response(result);
				
			}
			
		})
	}
	
});

//busca se tem mais produtos pagina produtos
var numeroPagina = 0;

$('#verMaisProdutos').on('click', function(){
	numeroPagina++
	$.ajax({
			method: "GET",
			url: "/produtos/list/ajax",
			data: {
				page: numeroPagina
			},
			beforeSend: function(){
				$("#loader-img").show();
			},
			success: function(response){
				if(response.length === 2){
				   $("#verMaisProdutos").hide();	
				}
				$("#listaProdutos").fadeIn(250, function(){
					$(this).append(response)
				});
				$("#loader-img").removeClass("loader");
			}
			
		})
});

/*mascara do formulario adicionar cartao de credito*/
$(document).ready(function(){
	$('#numCartao').mask('0000 0000 0000 0000', { reverse: true });
	$('#codigoCartao').mask('000', { reverse: true });
})



/*verificando se o ususario marcou uma forma de pagamento */
let boleto = document.getElementById("boleto")
		if(boleto != null){
	        let pix    = document.getElementById("pix")
	        let cartao = document.getElementById("cartao")
	
	        document.getElementById("confirmar").addEventListener('click', function(){
			if(cartao === null){
				cartao = false;
			}
				
	           if(boleto.checked || pix.checked || cartao.checked){
	           		let formaPagamento
	           		if(boleto.checked){
	           			formaPagamento = "Boleto";
	           		}else if(pix.checked){
	           			formaPagamento = "Pix";
	           		}else{
	           		 	formaPagamento = "Cartão de Crédito";
	           		}		
	           		
	          			$.ajax({
								method: "GET",
								url: "/vendas/pagamento/save",
								data: {
									forma: formaPagamento
								},
								success: function(response){
									console.log(response)	
									location.href = "/vendas/finalizar/pedido"							
								}
			
						})
	           }else{
					$('#mensagem').html('Por favor selecione uma forma de pagamento')
					$("#md").click();
					
	               //alert("Selecione a forma de pagamento")
	           }
	        }) 
	     }


/*Pagina finalizar pedido checando qual endereço esta marcado */
$('#finalizarCompra').on('click', function(){
	let inputs = document.getElementsByClassName("endereco")
	
	let valorId;
	for(var i=0; i<inputs.length; i++){
		if(inputs[i].checked == true){
			valorId = inputs[i].value;	
		}
	}
	if(valorId === undefined){
		$('#mensagem').html('Selecione um endereço antes de Confirmar o Pedido.')
		$("#md").click();		
		//alert("Selecione um endereço antes de Confirmar o Pedido.")
	}else{
		enviarEndereco(valorId)
	}
	
	
})
/*função que adiciona endereço de entrega */
function enviarEndereco(valorId){
	$.ajax({
			method: "GET",
			url: "/vendas/add/endereco/entrega",
			data: {
				id: valorId
			},
			success: function(response){
				console.log(response)	
				location.href = "/vendas/save"							
			}
	
	})
}

/*Pagina de cadastro de produtos */

