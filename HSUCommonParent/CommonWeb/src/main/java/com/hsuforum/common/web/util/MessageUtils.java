package com.hsuforum.common.web.util;

import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ResourceBundleMessageSource;

/**
 * Message utility
 * 
 * @author Marvin
 *
 */
public class MessageUtils extends ResourceBundleMessageSource {

	public MessageUtils() {
		super.setBasenames(
				new String[] { "applicationResources", "messages" });
	}

	public static MessageSourceAccessor getAccessor() {
		return new MessageSourceAccessor(new MessageUtils());
	}

}
