package guru.qa.niffler.jupiter.extension;

import guru.qa.niffler.api.CategoryApiClient;
import guru.qa.niffler.jupiter.annotation.Category;
import guru.qa.niffler.jupiter.annotation.User;
import guru.qa.niffler.model.CategoryJson;
import org.junit.jupiter.api.extension.*;
import org.junit.platform.commons.support.AnnotationSupport;

import static guru.qa.niffler.utils.RandomDataUtils.randomCategoryName;

public class CategoryExtension implements BeforeEachCallback, AfterTestExecutionCallback, ParameterResolver {

    public static final ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create(CategoryExtension.class);

    private final CategoryApiClient categoryApiClient = new CategoryApiClient();

    @Override
    public void beforeEach(ExtensionContext context) {

        String categoryName = randomCategoryName();

        AnnotationSupport.findAnnotation(context.getRequiredTestMethod(), User.class)
                .ifPresent(anno -> {
                    Category firstElement = anno.categories()[0];
                    if (anno.categories().length > 0) {
                        CategoryJson createCategory = new CategoryJson(
                                null,
                                firstElement.title().isEmpty() ? categoryName : firstElement.title(),
                                anno.username(),
                                false
                        );

                        createCategory = categoryApiClient.addCategory(createCategory);
                        if (firstElement.archived()) {
                            createCategory = new CategoryJson(
                                    createCategory.id(),
                                    createCategory.name(),
                                    createCategory.username(),
                                    true
                            );
                            createCategory = categoryApiClient.updateCategory(createCategory);
                        }
                        context.getStore(NAMESPACE).put(context.getUniqueId(), createCategory);
                    }
                });
    }

    @Override
    public void afterTestExecution(ExtensionContext context) {
        CategoryJson category = context.getStore(NAMESPACE).get(context.getUniqueId(), CategoryJson.class);
        if (category != null) {
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
        return extensionContext.getStore(CategoryExtension.NAMESPACE).get(extensionContext.getUniqueId(), CategoryJson.class);
    }
}
