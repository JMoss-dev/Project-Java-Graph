package src;

import src.core.GraphCommandProvider;

class Main {
    public static void main(String[] args) {

        GraphCommandProvider graphCommandProvider = new GraphCommandProvider();
        graphCommandProvider.startListening();

    }
}