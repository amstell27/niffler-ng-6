package guru.qa.niffler.jupiter;

import com.github.javafaker.Faker;
import guru.qa.niffler.api.CategoryApiClient;
import guru.qa.niffler.model.CategoryJson;
import org.junit.jupiter.api.extension.*;
import org.junit.platform.commons.support.AnnotationSupport;

public class CreateCategoryExtension implements BeforeEachCallback, AfterTestExecutionCallback, ParameterResolver {

    public static final ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create(CreateCategoryExtension.class);

    private final CategoryApiClient categoryApiClient = new CategoryApiClient();
    private final Faker fakerCategory = new Faker();

    @Override
    public void beforeEach(ExtensionContext context) {
        AnnotationSupport.findAnnotation(context.getRequiredTestMethod(), Category.class)
                .ifPresent(anno -> {
                    CategoryJson createCategory = new CategoryJson(
                            null,
                            fakerCategory.funnyName().name(),
                            anno.username(),
                            anno.archived()
                    );
                    createCategory = categoryApiClient.addCategory(createCategory);

                    if (anno.archived()) {
                        createCategory = new CategoryJson(
                                createCategory.id(),
                                createCategory.name(),
                                createCategory.username(),
                                true
                        );
                        createCategory = categoryApiClient.updateCategory(createCategory);
                    }
                    context.getStore(NAMESPACE).put(context.getUniqueId(), createCategory);
                });
    }

    @Override
    public void afterTestExecution(ExtensionContext context) {
        CategoryJson category = context.getStore(NAMESPACE).get(context.getUniqueId(), CategoryJson.class);
        if (!category.archived()) {
            CategoryJson archiveCategory = new CategoryJson(
                    category.id(),
                    category.name(),
                    category.username(),
                    true
            );
            categoryApiClient.updateCategory(archiveCategory);
        }
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().isAssignableFrom(CategoryJson.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return extensionContext.getStore(CreateCategoryExtension.NAMESPACE).get(extensionContext.getUniqueId(), CategoryJson.class);
    }
}
