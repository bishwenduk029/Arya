import React from "react";
import {
  Box,
  Center,
  Container,
  Flex,
  HStack,
  Input,
  Text,
  VStack,
} from "@chakra-ui/react";
import Recorder from "./recorder";
import { useStoreState } from "easy-peasy";

const Chat = () => {
  const chats = useStoreState((state) => state.chats.items);
  return (
    <Container p={10} maxW="container.xl">
      <Center>
        <Box rounded={15} bg="#EDF0F6" w={"full"}>
          <VStack p={2}>
            <Box h="780px" w="100%" p={5} justify={"end"} overflowY={"auto"}>
              {chats.map((chat, index) => {
                return (
                  <Flex
                    key={index}
                    w="100%"
                    m={1}
                    p={2}
                    justify={index % 2 == 0 ? "end" : "start"}
                  >
                    <Box
                      shadow="md"
                      borderWidth="1px"
                      bg={index % 2 == 0 ? "#F66439" : "#fff"}
                      p={3}
                      rounded={"md"}
                    >
                      <Text
                        align={index % 2 == 0 ? "right" : "left"}
                        fontSize={"md"}
                        color={index % 2 == 0 ? "#fff" : "#000"}
                      >
                        {chat}
                      </Text>
                    </Box>
                  </Flex>
                );
              })}
            </Box>
            <Box w="100%">
              <Box bg="#fff" p={2} rounded="xl">
                <HStack>
                  <Box w="100%">
                    <Input
                      placeholder="Type Something..."
                      variant="unstyled"
                      size="md"
                      p={2}
                      fontSize="1.3em"
                    />
                  </Box>
                  <Box>
                    <Recorder />
                  </Box>
                </HStack>
              </Box>
            </Box>
          </VStack>
        </Box>
      </Center>
    </Container>
  );
};

export default Chat;
