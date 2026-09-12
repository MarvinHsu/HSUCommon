# HSUCommon

![Version](https://img.shields.io/badge/version-5.1.1-blue)
![Java](https://img.shields.io/badge/java-21-orange)
![Spring](https://img.shields.io/badge/spring-7.0.8-green)
![License](https://img.shields.io/badge/license-Apache%202.0-brightgreen)

A comprehensive **shared utilities library** for building enterprise Java applications with **type-safe, generic base classes** and utilities across all layers (Entity, DAO, Service, Web).

## 🎯 Overview

HSUCommon provides a **layered architecture foundation** with reusable base classes and utilities that enable rapid development of enterprise applications while maintaining **strict type safety** and **backward compatibility**.

### Key Highlights

✅ **Generic Type-Safe Base Classes** - Consistent patterns across all layers (Entity → DAO → Service → Web)  
✅ **Hybrid DAO Pattern Support** - Traditional DAO + Modern Spring Data JPA in unified interface  
✅ **Modern Spring Framework** - Spring 7.0.8, Spring Data JPA 4.1.1, Spring Security 7.1.0  
✅ **Jakarta EE 9+** - Full jakarta.* namespace support (not legacy javax.*)  
✅ **JSF & PrimeFaces** - Enterprise web UI components with managed beans  
✅ **Java 21 Ready** - Compiled and optimized for Java 21  
✅ **Backward Compatible** - Maintains legacy patterns while supporting modern approaches  

## 🏗️ Architecture

HSUCommon implements a **4-layer architecture** with generic base classes:

```
┌─────────────────────────────────────────┐
│         Web Layer (JSF/PrimeFaces)      │
│   TemplatePrimeDataTableManagedBean<T> │
└─────────────────────────────────────────┘
                    ↓
┌─────────────────────────────────────────┐
│       Service Layer (Spring)            │
│   BaseJpaServiceImpl<T, ID, REPO>       │
└─────────────────────────────────────────┘
                    ↓
┌─────────────────────────────────────────┐
│   DAO Layer (Spring Data JPA)           │
│   BaseJpaRepository<T, ID>             │
└─────────────────────────────────────────┘
                    ↓
┌─────────────────────────────────────────┐
│       Entity Layer (Jakarta JPA)        │
│   BaseEntityImpl<PK>                    │
└─────────────────────────────────────────┘
```

### Layer Components

#### 1. **Entity Layer** (`entity/`)
- `BaseEntity<PK>` - Interface defining entity contract
- `BaseEntityImpl<PK>` - Abstract base implementation
- `SystemDateOperation` - Marker interface for audit tracking
- `SystemDateEntityListener` - Auto-manages created/modified timestamps

#### 2. **DAO Layer** (`dao/`)
- `BaseDao<T, PK>` - Traditional data access interface
- `BaseJpaRepository<T, ID>` - Spring Data JPA interface
- `BaseDaoImpl<T, PK>` - Hybrid implementation

#### 3. **Service Layer** (`service/`)
- `BaseJpaService<T, ID>` - Modern JPA service interface (recommended for new code)
- `BaseService<T, PK>` - Legacy service interface (backward compatible)
- `BaseFacade<T, PK>` - Lightweight facade interface
- Implementations in `impl/` folder with transaction management

#### 4. **Web Layer** (`web/`)
- **JSF ManagedBeans:** `BaseManagedBeanImpl`, `TemplatePrimeDataTableManagedBean`
- **Value Objects:** `ValueObject`, `VoWrapper` for data transfer
- **Utilities:** JSF helpers, date/time utilities, string utilities, encryption, i18n support

## 📦 Tech Stack

| Component | Version | Scope |
|-----------|---------|-------|
| **Java** | 21 | Compile Target |
| **Spring Framework** | 7.0.8 | Provided |
| **Spring Data JPA** | 4.0.6 | Provided |
| **Spring Security** | 7.1.0 | Provided |
| **Jakarta EE** | 9+ | Provided |
| **Jakarta JPA** | 3.2.0 | Provided |
| **Jakarta Faces (JSF)** | 4.1.9 | Provided |
| **PrimeFaces** | 15.0.16 | Provided |
| **Apache Commons Lang** | 3.19.0 | Provided |
| **Velocity** | 2.3 | Provided |

## 🚀 Quick Start

### 1. Add Dependency

```xml
<dependency>
    <groupId>com.hsuforum</groupId>
    <artifactId>HSUCommon</artifactId>
    <version>5.1.1</version>
</dependency>
```

### 2. Create an Entity

```java
import com.hsuforum.common.entity.impl.BaseEntityImpl;
import jakarta.persistence.Entity;

@Entity
public class Product extends BaseEntityImpl<String> {
    private String id;
    private String name;
    private double price;
    
    @Override
    public String getId() { return id; }
    
    @Override
    public void setId(String id) { this.id = id; }
    
    // Getters and setters
}
```

### 3. Create a Repository

```java
import com.hsuforum.common.dao.BaseJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends BaseJpaRepository<Product, String> {
    List<Product> findByName(String name);
}
```

### 4. Implement a Service

```java
import com.hsuforum.common.service.impl.BaseJpaServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class ProductService extends BaseJpaServiceImpl<Product, String, ProductRepository> {
    
    public ProductService(ProductRepository repository) {
        super(repository);
    }
    
    public List<Product> findActiveProducts() {
        return this.findAll((root, query, cb) -> 
            cb.isTrue(root.get("active"))
        );
    }
}
```

### 5. Build a Web UI (ManagedBean)

```java
import com.hsuforum.common.web.jsf.managedbean.impl.TemplatePrimeDataTableManagedBean;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

@Named("productBean")
@ViewScoped
public class ProductManagedBean extends TemplatePrimeDataTableManagedBean<
    Product, String, ProductService, ProductService> {
    
    @Override
    public String doCreateAction() {
        // Inherited: initializes new product and navigates to create page
        return super.doCreateAction();
    }
    
    @Override
    public String doSaveCreateAction() {
        // Inherited: saves product and navigates back to list
        return super.doSaveCreateAction();
    }
}
```

## 💡 Generic Type Pattern

HSUCommon maintains **consistent generic patterns** across all layers:

```
Entity:     extends BaseEntityImpl<T>
Repository: extends BaseJpaRepository<T, ID>
Service:    extends BaseJpaServiceImpl<T, ID, REPO>
ManagedBean: extends BaseManagedBeanImpl<T, ID, SERVICE, JPA_SERVICE>
```

**Type Parameters:**
- `T` - Entity type
- `ID` (or `PK`) - Primary key type (typically String, Long, UUID)
- `SERVICE` - Service class (BaseService or BaseJpaService)
- `REPO` - Repository class extending BaseJpaRepository

## 🔍 Core Features

### Query Flexibility

```java
// Method 1: Using Specification (Type-safe JPA Criteria)
List<Product> results = productService.findAll(
    (root, query, cb) -> cb.equal(root.get("status"), "active")
);

// Method 2: Using Example Pattern (Domain-driven)
Product example = new Product();
example.setStatus("active");
List<Product> results = productService.findAll(Example.of(example));

// Method 3: Pagination and Sorting
Page<Product> page = productService.findAll(
    specification,
    PageRequest.of(0, 20, Sort.by("createdDate").descending())
);
```

### Automatic Audit Tracking

```java
@Entity
public class AuditedEntity extends BaseEntityImpl<String> implements SystemDateOperation {
    // Automatically tracked by SystemDateEntityListener
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
}
```

### Utility Helpers

```java
import com.hsuforum.common.web.util.*;

// Date/Time utilities
LocalDate date = DateUtils.parseDate("2024-01-01");
String formatted = DateFormatUtils.format(date, "yyyy-MM-dd");

// String utilities
boolean isEmpty = StringUtils.isBlank(input);

// Encryption
String encrypted = EncryptUtils.encrypt(password);

// JSF Integration
JSFMessageUtils.addSuccessMessage("Operation completed");
JSFUtils.redirect("products.xhtml");
```

## 🛠️ Build & Deployment

### Build

```bash
# Full build
mvn clean package

# Run tests
mvn test

# Install to local repository
mvn install

# Skip tests
mvn clean package -DskipTests
```

### Output

- **JAR:** `target/HSUCommon-5.1.1.jar`
- **Target:** Java 21 compatible, ready for use as a shared dependency

## 📚 Documentation

### Project Guides
- **[copilot-instructions.md](.github/copilot-instructions.md)** - Comprehensive AI agent guide with patterns, conventions, and best practices
- **[AGENTS.md](AGENTS.md)** - Specialized agent roles and workflows for different development tasks

### Key Patterns Documented
- Generic type consistency across layers
- Hybrid DAO/JPA pattern support
- Service transaction boundaries
- JSF ManagedBean CRUD operations
- Value object design patterns
- Pagination and sorting strategies
- Query specification patterns

## ✨ Best Practices

### Do ✅

- Extend `BaseJpaServiceImpl<T, ID, REPO>` for new services
- Use `Specification<T>` or `Example<T>` for flexible queries
- Implement `SystemDateOperation` for audit-tracked entities
- Transaction management is handled via AOP configuration
- Inject dependencies via constructor in services
- Use Jakarta EE 9+ imports (`jakarta.*`)
- Keep ManagedBeans focused on presentation logic
- Maintain generic type order: `<T, ID, SERVICE, JPA_SERVICE>`

### Don't ❌

- Don't use `javax.*` packages (use `jakarta.*` instead)
- Don't forget `getId()`/`setId()` implementation in entities
- Don't mix traditional DAO and JPA patterns in same service
- Don't remove public methods from base classes (breaking change)
- Don't use field injection in services (use constructor injection)
- Don't hardcode pagination/sorting logic in ManagedBeans
- Don't bypass transaction boundaries for data operations
- Don't ignore `serialVersionUID` in entity classes

## 🔐 Backward Compatibility

HSUCommon maintains **100% backward compatibility** with existing implementations:

- **Legacy DAO Pattern:** `BaseService<T, PK>` and `BaseDao<T, PK>` fully supported
- **ServiceLocator:** Available for legacy service discovery patterns
- **Traditional Query Strings:** StringBuffer-based queries still work
- **All Utilities:** DateUtils, StringUtils, etc. extend Apache Commons

New code should prefer modern Spring Data JPA patterns, but legacy patterns remain fully functional.

## 🤝 Contributing

When extending HSUCommon:

1. **Maintain generic type consistency** across layers
2. **Preserve backward compatibility** - add features, don't remove public methods
3. **Follow Spring conventions** - dependency injection, transactional boundaries
4. **Use Jakarta EE 9+** - no javax.* imports
5. **Document complex patterns** with code examples
6. **Test thoroughly** - base classes affect all consuming projects

See [AGENTS.md](AGENTS.md) for detailed workflow guides per role.

## 📋 Version History

| Version | Release Date | Java | Spring | Key Changes |
|---------|--------------|------|--------|-------------|
| 5.1.1 | 2026-08-31 | 21 | 7.0.8 | Current stable release |
| 5.1.0 | Earlier | 21 | 7.0.8 | Jakarta EE 9+ migration |

## 📄 License

Apache License 2.0 - See [LICENSE](LICENSE) file for details

## 📞 Support

For questions about HSUCommon patterns and conventions, refer to:
- **[copilot-instructions.md](.github/copilot-instructions.md)** - Technical reference
- **[AGENTS.md](AGENTS.md)** - Development workflows by role
- **Source code** - Inline documentation and examples

---

**HSUCommon v5.1.1** | Shared Utilities Library for Enterprise Java Applications | Java 21 • Spring 7.0.8 • Jakarta EE 9+