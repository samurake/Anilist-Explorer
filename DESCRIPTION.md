# AniList Explorer

## Overview

AniList Explorer is a Compose-first Android application that reads anime data from AniList GraphQL and presents two main flows: a discovery-focused home screen and a details screen for a selected title. The codebase is organized into modules so UI, domain contracts, data access, and GraphQL integration stay separated and testable.

The implementation follows the assignment board at [Superthread](https://app.superthread.com/samurake-phufpfob5n/board-4-anilist-explorer), uses the visual direction from the [Movie Mobile App UI Design Figma](https://www.figma.com/design/cLub3jrsAqCdQzURbFvcsc/Movie-Mobile-App-UI-Design--Community-?node-id=1-109&t=hrIXGQgjPASCLcH1-0), and integrates AniList GraphQL using the Apollo sandbox endpoint documented [here](https://studio.apollographql.com/sandbox/explorer?endpoint=https://graphql.anilist.co&explorerURLState=N4IgJg9gxgrgtgUwHYBcQC4TADpIAR4AKAhgOYJ474F6JgCWxluNNAzvSggKoBOANi1Z4UnfhSrCayUv3psAFkKlJiogG4JlNAL7aCYBGyi96AB1EQk2vdVs6QAGhDrip4gCNxbDFmy9cBx0gA).

The assignment references a `:model` module, but this repository uses `:domain` for shared business models, resource wrappers, repository contracts, and use cases. Functionally, `:domain` fills the responsibility that a dedicated model/domain module would normally cover.

## Architecture Overview

The app follows a layered MVVM structure:

1. `MainActivity` hosts the Compose navigation graph.
2. Screen-level `ViewModel`s expose immutable UI state plus one-off events.
3. Use cases in `:domain` delegate to the repository contract.
4. `AnimeRepositoryImpl` in `:data` coordinates Apollo GraphQL calls and maps transport models into domain models.
5. Compose screens render UI from the latest `StateFlow` snapshot and react to events such as navigation and snackbar messages.

This keeps Android framework concerns in `:app`, networking in `:graphql`, and data/model transformations in `:data` plus `:domain`.

## Module Responsibilities

### `:app`

- Application entry point, Hilt setup, activity, navigation graph
- Compose UI, theme, resources, and screen-specific `ViewModel`s
- Instrumentation tests and UI-focused unit tests

### `:data`

- `AnimeRepositoryImpl`
- GraphQL-to-domain mapping logic
- Data-layer tests for repository behavior and mapper correctness

### `:domain`

- Domain models such as `AnimeSummary`, `AnimeDetails`, `HomeSection`, `Character`, and `Genre`
- `Resource` wrapper for success/error/loading outcomes
- Repository interface and simple use cases

### `:graphql`

- Apollo client configuration
- OkHttp setup
- Dispatcher qualifiers
- Mock server support and generated GraphQL models/queries

## GraphQL Integration Approach

Apollo Kotlin generates strongly typed query models from the AniList schema. The repository uses those generated models directly, executes queries through `ApolloClient`, checks for GraphQL-level errors, and then maps successful payloads into stable domain models.

That mapping layer is intentionally kept in `:data` so UI code does not depend on generated GraphQL types. If the schema changes, the impact is mostly contained to generated classes and mapper functions instead of leaking through the app.

## Single Source of Truth

The repository is the single source of truth for fetched anime data within the current app scope. `ViewModel`s never build data independently from transport models and never reach into networking directly. Instead, they request data through use cases, receive domain-level `Resource` results, and expose a single observable `uiState`.

In practice, the SSOT flow looks like this:

1. UI emits an intent.
2. `ViewModel` calls a use case.
3. Repository fetches and maps data.
4. `ViewModel` publishes the latest state snapshot.
5. Compose recomposes from that state.

The retry tests cover this behavior by verifying that a subsequent repository result replaces the previous error state instead of mixing old and new representations.

## Mocking Strategy

Two complementary mocking approaches are used:

- Unit tests use fake repositories to isolate `ViewModel` behavior and state transitions.
- Data-layer repository tests use `MockWebServer` to exercise real Apollo parsing against controlled GraphQL responses.

For runtime development builds, the `:graphql` module also exposes a local mock server (`MockServerManager` + `MockJsonProvider`). The `development` flavor points Apollo to that mock source so the app can be run without depending on the live AniList backend for the main demo flows.

## Product Flavors And Build Types

The project defines two flavors under the `environment` dimension:

- `development`: enables mock data through `BuildConfig.IS_MOCK = true`
- `production`: uses the real AniList GraphQL endpoint with `BuildConfig.IS_MOCK = false`

The main build types are:

- `debug`: debuggable, no minification, no resource shrinking
- `release`: non-debuggable, minified, resource-shrunk, and optimized with ProGuard/R8

`productionRelease` is therefore the optimized variant used to validate obfuscation, shrinking, and logging removal. It remains unsigned unless a signing config is added later for distribution.

## Testing Strategy

### Unit tests

The automated unit suite covers:

- `HomeViewModel` state handling and snackbar/navigation events
- `DetailsViewModel` state handling, retry behavior, and event emission
- GraphQL model mapping into domain models
- Repository behavior for success and GraphQL error scenarios
- SSOT-oriented retry/data refresh behavior via `ViewModel` state replacement tests

Robolectric is used in the `:app` unit tests so Android resources and `Application` strings can be exercised without a device. Coroutine tests use a controllable main dispatcher rule.

### Instrumentation tests

The instrumentation suite targets the main user journeys on device/emulator:

- Home screen rendering
- Navigation from Home to Details
- Details screen rendering
- Basic interaction flows such as disabled action snackbar feedback

These tests run against the real app shell and Compose navigation graph using the `developmentDebug` flavor, which makes the flows deterministic by serving mock GraphQL content.

## Trade-offs

- The repository currently returns mapped domain objects rather than a reactive local cache-backed stream. That keeps the implementation small, but it is a lighter SSOT than an offline-first database-backed approach.
- Home section titles are currently assembled in the repository instead of being fully localized through resources/domain metadata.
- Details actions intentionally stop at snackbar feedback because the assignment does not require trailer playback, favorites persistence, or profile/saved flows.
- The app uses module boundaries and use cases even though the project size is relatively small. This adds a bit of ceremony, but it makes the architecture easier to explain and extend.

## Future Improvements

- Add a persistent local cache and expose reactive streams for a stronger offline-first SSOT
- Localize remaining repository-owned display strings
- Add paging for larger lists and richer search/filter capabilities
- Improve UI crispness and motion smoothness with a final polish pass on transitions, loading states, and perceived responsiveness
- Replace placeholder interactions with real favorites, watchlist, and trailer behavior
- Add CI execution for unit tests plus emulator-backed instrumentation tests
- Expand production hardening with stricter network retry, structured error taxonomy, and baseline profiles
