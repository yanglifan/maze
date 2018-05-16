package com.github.navi.core.matcher;

import com.github.navi.core.MatchResult;
import org.junit.Test;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class EqualsMatcherTests {

	private EqualsMatcher.Processor equalsMatcherProcessor = new EqualsMatcher.Processor();

	private EqualsMatcher usernameEqualsStark = new EqualsMatcher() {
		@Override
		public String propertyPath() {
			return "username";
		}

		@Override
		public String expectValue() {
			return "stark";
		}

		@Override
		public Class<? extends Annotation> annotationType() {
			return null;
		}
	};


	@Test
	public void simple() {
		TestRequest testRequest = new TestRequest("stark");
		MatchResult matchResult = equalsMatcherProcessor.process(testRequest, usernameEqualsStark);
		assertThat(matchResult).isEqualTo(MatchResult.ACCEPT);
	}

	@Test
	public void nested_prop() {
		// Given
		Map<String, Object> request = new HashMap<>();
		request.put("propLevel1", new TestRequest("stark"));

		EqualsMatcher equalsMatcher = new EqualsMatcher() {
			@Override
			public String propertyPath() {
				return "propLevel1.username";
			}

			@Override
			public String expectValue() {
				return "stark";
			}

			@Override
			public Class<? extends Annotation> annotationType() {
				return null;
			}
		};

		// When
		MatchResult matchResult = equalsMatcherProcessor.process(request, equalsMatcher);

		// Then
		assertThat(matchResult).isEqualTo(MatchResult.ACCEPT);
	}

	@Test
	public void doMatchWithMapRequest() {
		Map<String, String> mapRequest = new HashMap<>();
		mapRequest.put("username", "stark");
		MatchResult matchResult = equalsMatcherProcessor.process(mapRequest, usernameEqualsStark);
		assertThat(matchResult).isEqualTo(MatchResult.ACCEPT);
	}

	public class TestRequest {
		private String username;

		TestRequest(String username) {
			this.username = username;
		}

		/**
		 * Used via reflection.
		 */
		@SuppressWarnings("all")
		public String getUsername() {
			return username;
		}
	}
}