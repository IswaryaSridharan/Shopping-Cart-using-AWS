package com.org.cart.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.amazonaws.services.lambda.runtime.Context;
import com.google.gson.Gson;
import com.org.cart.dao.DynamoDBManager;
import com.org.cart.exception.BadRequestException;
import com.org.cart.exception.DAOException;
import com.org.cart.model.LambdaRequest;
import com.org.cart.model.cart.Cart;
import com.org.cart.model.cart.ParseJson;
import com.org.cart.model.cart.CartDAO;
import com.org.cart.model.cart.ReadCartResponse;

public class CreateCartAction extends AbstractAction{

	static final Logger LOGGER = LoggerFactory.getLogger(CreateCartAction.class);

	private static final String LOGINID = "loginId";

	private static final String SKU = "sku";

	private static final String SUCCESS = "200";


	@Override
	public String handle(LambdaRequest lambdaRequest, Context context)
			throws BadRequestException,DAOException, Exception {

		validateRequest(lambdaRequest);

//		Cart cartBody = new Gson().fromJson(lambdaRequest.getBody(), Cart.class);
//		body should be like below
//      jsonString = "{'loginId' : 'iswarya', 'sku' : 'test', 'items' : [{'id':1, 'price':10}]}", {'id':2, 'price':20}]}";

		Cart cart = new ParseJson().parse(lambdaRequest.getBody());
		CartDAO dao = DynamoDBManager.getCartDAO();

		try {
			dao.createCart(cart);
		} catch (Exception e) {

			LOGGER.info("Error while creating the cart" + e.getMessage());
			throw new BadRequestException("ERROR CREATING THE CART ITEM");
		}

		ReadCartResponse readCartResponse = new ReadCartResponse();
		readCartResponse.setLoginId(cart.getLoginId());
		readCartResponse.setCode(SUCCESS);
		readCartResponse.setTotalCost(cart.computeTotalCost());

		return new Gson().toJson(readCartResponse);
	}

	private void validateRequest(LambdaRequest lambdaRequest) throws BadRequestException {
		LOGGER.info("Request body " + lambdaRequest.getBody());
		if(!lambdaRequest.getBody().has(LOGINID)) {
			throw new BadRequestException("NO LOGINID PARAM PRESENT");
		}
		if(!lambdaRequest.getBody().has(SKU)) {
			throw new BadRequestException("NO SKU PARAM PRESENT");
		}
		if(StringUtils.isEmpty(lambdaRequest.getBody().get(LOGINID).getAsString())){
			throw new BadRequestException("EMPTY LOGINID PRESENT");
		}

		if(StringUtils.isEmpty(lambdaRequest.getBody().get(SKU).getAsString())){
			throw new BadRequestException("EMPTY SKU PRESENT");
		}

	}

}
