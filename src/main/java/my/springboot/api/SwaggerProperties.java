package my.springboot.api;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Swagger Properties
 *
 * @author xiegang
 * @since 16/5/31
 */
@ConfigurationProperties(prefix = "swagger.ui")
public class SwaggerProperties {
    private boolean enable = true;

    private String group = "webapi";

    private String title = "[set a api title via 'swagger.ui.title']";

    private String description = "[add your api description via 'swagger.ui.description']";

    private String version = "1.0.0";

    private String termsOfServiceUrl = "[set termsOfServiceUrl via 'swagger.ui.termsOfServiceUrl']";

    private String contact = "[set contact via 'swagger.ui.contact']";

    private String license = "License";

    private String licenseUrl = "https://opensource.org/licenses/MIT";

    private String excludes = "/error*";

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getTermsOfServiceUrl() {
        return termsOfServiceUrl;
    }

    public void setTermsOfServiceUrl(String termsOfServiceUrl) {
        this.termsOfServiceUrl = termsOfServiceUrl;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getLicenseUrl() {
        return licenseUrl;
    }

    public void setLicenseUrl(String licenseUrl) {
        this.licenseUrl = licenseUrl;
    }

    public String getExcludes() {
        return excludes;
    }

    public void setExcludes(String excludes) {
        this.excludes = excludes;
    }
}
