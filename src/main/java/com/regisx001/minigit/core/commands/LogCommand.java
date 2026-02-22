package com.regisx001.minigit.core.commands;

import java.nio.charset.StandardCharsets;

import com.regisx001.minigit.core.Command;
import com.regisx001.minigit.core.Repository;
import com.regisx001.minigit.core.RepositoryLoader;
import com.regisx001.minigit.filesystem.FileSystemService;
import com.regisx001.minigit.storage.ObjectStore;
import com.regisx001.minigit.storage.RefStore;

public class LogCommand implements Command {

    @Override
    public void execute() {
        Repository repo = new RepositoryLoader().load();
        FileSystemService fs = new FileSystemService();
        ObjectStore store = new ObjectStore(repo.objectsDir(), fs);

        RefStore refs = new RefStore(repo, fs);
        String current = refs.readCurrentCommit();
        if (current == null) {
            System.out.println("No commits yet.");
            return;
        }

        while (current != null) {
            byte[] data = store.read(current);
            String text = new String(data, StandardCharsets.UTF_8);

            System.out.println("commit " + current);
            System.out.println(text.split("\n\n", 2)[1]);
            System.out.println();

            current = text.contains("parent ")
                    ? text.split("parent ")[1].split("\n")[0]
                    : null;
        }

    }

}
