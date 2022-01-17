import * as React from "react";
import { StoreProvider } from "easy-peasy";
import { ChakraProvider } from "@chakra-ui/react";
import store from './state'
import WebSocketWrapper from "./webSocketWrapper";

function App() {
  // 2. Use at the root of your app
  return (
    <ChakraProvider>
      <StoreProvider store={store}>
        <WebSocketWrapper />
      </StoreProvider>
    </ChakraProvider>
  );
}

export default App