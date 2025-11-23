# 📱 Filmes API - Jetpack Compose

> **Projeto:** app de catálogo de filmes (consome API), feito com Jetpack Compose + Ktor + Koin + Navigation3.  
> Objetivo: tela de listagem (com shimmer), tela de detalhes, navegação com Navigation3, e cobertura completa de testes (unit, instrumented e snapshot).

## ⚙️ Tecnologias e libs principais

- **Kotlin** (2.2.20)
- **Android Gradle Plugin** )8.13.1)
- **Jetpack Compose** (Material3)
- **Ktor** (cliente HTTP)
- **Coil 3**
- **Koin** (injeção com anotações + KSP)
- **Navigation 3**
- **compose-shimmer**
- **FancyJetpackComposeDialog**
- **Detekt**
- **Shot (Karumi)**
- **MockK**
- **JUnit4**
- **Espresso**

---

## 🗂️ Estrutura do projeto

- presentation (UI, screens, components)
- data (api, datasource, repository, mappers, models)
- domain (viewData, interfaces)
- di (Koin modules)
- test / androidTest

---

## ⚙️ Funcionalidades
- Buscar filmes via Ktor
- Shimmer placeholder
- Lista categorizada
- Tela de detalhes
- Diálogos de erro
- Koin DI
- Testes completos (unit + instrumented + snapshot)

---

## 🚀 Como rodar o projeto

1. Clone este repositório:
   ```bash
   git clone https://github.com/marcelo-souza-1999/filmes-api-jetpack-compose
   ```
2. Abra o projeto no **Android Studio**.
3. Certifique-se de que o JDK 21 e as dependências necessárias estão instalados.
4. Execute o projeto em um emulador ou dispositivo físico.

---

## 📸 Funcionalidades em Vídeo

<details>
<summary>📽️ Clique para visualizar o vídeo</summary>

https://github.com/user-attachments/assets/e3148c1f-04b7-42a0-9464-51502a42df32

</details>

---

## ✅ Testes

### Unit tests
```bash
./gradlew test
```

### Instrumented tests
```bash
./gradlew connectedDebugAndroidTest
```

### Snapshot tests (Shot)
Este projeto já possui imagens de baseline salvas para os testes de snapshot.

- Para rodar os testes de snapshot e validar a UI:
  ```bash
  ./gradlew executeScreenshotTests
  ```  

- Caso tenha feito alterações na interface e queira atualizar as imagens de baseline:
  ```bash
  ./gradlew executeScreenshotTests -Precord
  ```  
Isso irá gerar novas imagens de referência para os testes.

---

## 📫 Contribuindo
Contribuições são bem-vindas! Para contribuir:

1. Realize o fork deste repositório.

2. Crie um branch com suas alterações:
   ```bash
   git checkout -b minha-contribuicao
   ```
3. Faça suas alterações e confirme:
   ```bash
   git commit -m "Minha contribuição"
   ```
4. Envie para o branch original:
   ```bash
   git push origin minha-contribuicao
   ```
5. Abra uma **Pull Request** no repositório principal.

---

## 📧 Contato
- **Nome**: Marcelo Souza
- **Email**: [marcelocaregnatodesouza@gmail.com](mailto:marcelocaregnatodesouza@gmail.com)
- **LinkedIn**: [Clique aqui](https://www.linkedin.com/in/marcelosouza-1999/)
