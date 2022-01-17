import { Box } from "@chakra-ui/react";
import { useStoreActions } from "easy-peasy";
import * as React from "react";
import Sockette from "sockette";
import Chat from "./Chat";

export const SocketContext = React.createContext(null);

function WebSocketWrapper() {
  const addNewChatPayload = useStoreActions((actions) => actions.chats.add);
  const socket = React.useRef(
    new Sockette("ws://localhost:8080/ws/chat/audio", {
      timeout: 5e3,
      maxAttempts: 10,
      onopen: (e) => console.log("Connected!", e),
      onmessage: (e) => {
        console.log("Received:", e);
        handleMessageFromServer(e.data)
      },
      onreconnect: (e) => console.log("Reconnecting...", e),
      onmaximum: (e) => console.log("Stop Attempting!", e),
      onclose: (e) => console.log("Closed!", e),
      onerror: (e) => console.log("Error:", e),
    })
  );

  function handleMessageFromServer(answer) {
      addNewChatPayload(answer)
  }

  return (
    <SocketContext.Provider value={{ socket: socket.current }}>
      <Box>
        <Chat />
      </Box>
    </SocketContext.Provider>
  );
}

export default WebSocketWrapper;
