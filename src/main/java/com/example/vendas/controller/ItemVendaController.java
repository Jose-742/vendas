package com.example.vendas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.vendas.model.repository.ItemVendaRepository;


@Transactional
@Controller
@RequestMapping("ItemVenda")
public class ItemVendaController {

	@Autowired
	private ItemVendaRepository  itemRepository;

}
