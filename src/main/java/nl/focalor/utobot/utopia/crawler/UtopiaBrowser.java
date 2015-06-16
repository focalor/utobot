package nl.focalor.utobot.utopia.crawler;

import java.util.List;
import java.util.stream.Collectors;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebClientOptions;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.gargoylesoftware.htmlunit.html.HtmlUnorderedList;

/**
 * @author focalor
 */
public class UtopiaBrowser {
	private final String username;
	private final String password;
	private final WebClient webClient;

	public UtopiaBrowser(String username, String password) {
		this.username = username;
		this.password = password;
		this.webClient = new WebClient(BrowserVersion.FIREFOX_31);

		WebClientOptions options = webClient.getOptions();
		options.setActiveXNative(false);
		options.setAppletEnabled(false);
		options.setCssEnabled(false);
		options.setJavaScriptEnabled(true);
		options.setThrowExceptionOnScriptError(false);
		options.setPopupBlockerEnabled(false);
	}

	public boolean login() {
		try {
			HtmlPage page = webClient.getPage("http://utopia-game.com/");
			HtmlForm loginForm = page.getForms().get(0);
			HtmlTextInput username = loginForm.getInputByName("username");
			HtmlPasswordInput password = loginForm.getInputByName("password");
			username.setText(this.username);
			password.setText(this.password);

			// Random sleep to avoid detection
			Thread.sleep((int) (Math.random() * 7000 + 3000));
			List<HtmlSubmitInput> elements = loginForm.getElementsByAttribute("input", "type", "submit");
			page = elements.get(0).click();

			// Mimic real user
			Thread.sleep((int) (Math.random() * 2000 + 500));
			webClient.getPage("http://utopia-game.com/wol/chooser/");
			Thread.sleep((int) (Math.random() * 1500 + 300));
			webClient.getPage("http://utopia-game.com/wol/game/throne");

			return true;
		} catch (Exception ex) {
			// TODO log
			return false;
		}
	}

	public List<String> getKingdomNews() {
		try {
			// TODO login check
			Thread.sleep((int) (Math.random() * 2000 + 500));
			HtmlPage page = webClient.getPage("http://utopia-game.com/wol/game/kingdom_news");
			HtmlTable newsTable = page.getElementById("news", true);
			List<HtmlTableRow> newsItems = newsTable.getRows();
			//@formatter:off
			return newsItems.stream()
					.map(item -> item.asText())
					.collect(Collectors.toList());
			//@formatter:on
		} catch (Exception ex) {
			return null;
			// TODO log
		}
	}

	public List<String> getProvinceNews() {
		try {
			// TODO login check
			Thread.sleep((int) (Math.random() * 2000 + 500));
			HtmlPage page = webClient.getPage("http://utopia-game.com/wol/game/province_news");
			HtmlTable newsTable = page.getElementById("news", true);
			List<HtmlTableRow> newsItems = newsTable.getRows();
			//@formatter:off
			return newsItems.stream()
					.map(item -> item.asText())
					.collect(Collectors.toList());
			//@formatter:on
		} catch (Exception ex) {
			return null;
			// TODO log
		}
	}

	@SuppressWarnings("unchecked")
	public String castSpell(String spell) {
		try {
			// TODO login check
			Thread.sleep((int) (Math.random() * 2000 + 500));
			HtmlPage page = webClient.getPage("http://utopia-game.com/wol/game/enchantment");
			HtmlSelect spellSelect = page.getElementById("id_spell", true);
			spellSelect.setSelectedAttribute(spell, true);

			HtmlForm form = page.getForms().get(1);
			List<HtmlSubmitInput> elements = form.getElementsByAttribute("input", "type", "submit");
			page = elements.get(0).click();

			// Check errors
			List<HtmlUnorderedList> errors = (List<HtmlUnorderedList>) page
					.getByXPath("\\ul[contains(@class, \"errorlist\")]");
			if (!errors.isEmpty()) {
				return errors.get(0).asText();
			}
			// TODO
			return "";
		} catch (Exception ex) {
			throw new RuntimeException(ex);
			// TODO log
		}
	}

	public static void main(String[] args) {
		UtopiaBrowser browser = new UtopiaBrowser("focalori", "login1");
		browser.login();
		System.err.println(browser.castSpell("MINOR_PROTECTION"));
		// List<String> news = browser.getKingdomNews();
		// news.stream().forEach(item -> System.err.println(item));
	}
}
