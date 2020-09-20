package com.github.kervincandido.forum.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TokenExtractorServiceTest {
	
	@Autowired
	private TokenExtractorService tokenExtractorService;
	
	@Test
	public void deveExtrairTokenDaRequisicaoEDevolver() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
		request.addHeader("Authorization", "Bearer token");
		
		String extractRequestToken = tokenExtractorService.extractRequestToken(request);
		assertThat(extractRequestToken, is(notNullValue()));
		assertThat(extractRequestToken, is(equalTo("token")));
	}
	
	@Test
	public void deveExtrairTokenDeStringEDevolver() {
		String extractRequestToken = tokenExtractorService.extractRequestToken("Bearer token");
		assertThat(extractRequestToken, is(notNullValue()));
		assertThat(extractRequestToken, is(equalTo("token")));
	}
	
	@Test
	public void devolveNullSeNaoTiverTokenNaRequisicao() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
		
		String extractRequestToken = tokenExtractorService.extractRequestToken(request);
		assertThat(extractRequestToken, is(nullValue()));
	}
	
	@Test
	public void devolveNullSeNaoTiverToken() {
		String extractRequestToken = tokenExtractorService.extractRequestToken((String)null);
		assertThat(extractRequestToken, is(nullValue()));
	}
}
