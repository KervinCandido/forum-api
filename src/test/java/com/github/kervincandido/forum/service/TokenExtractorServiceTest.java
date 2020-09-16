package com.github.kervincandido.forum.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TokenExtractorServiceTest {
	
	@Autowired
	private TokenExtractorService tokenExtractorService;
	
	@MockBean
	private HttpServletRequest request;
	
	@Test
	public void deveExtrairTokenDaRequisicaoEDevolver() {
		when(request.getHeader("Authorization"))
			.thenReturn("Bearer token");
		
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
		when(request.getHeader("Authorization"))
			.thenReturn(null);
		
		String extractRequestToken = tokenExtractorService.extractRequestToken(request);
		assertThat(extractRequestToken, is(nullValue()));
	}
	
	@Test
	public void devolveNullSeNaoTiverToken() {
		String extractRequestToken = tokenExtractorService.extractRequestToken((String)null);
		assertThat(extractRequestToken, is(nullValue()));
	}
}
