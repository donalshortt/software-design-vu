package eu.donals;

import java.util.List;


public class Location {
    private String name;
    private String description;
    private NPC npc;
    private List<String> neighbours;

    public class NPC {
        private String name;
        List<String> image;
        private String about;
        private String item;
        private String riddle;
        private String answer;
        private String returnDialog;

        public String getName() { return this.name; }
        public List<String> getImage() { return image; }
        public String getAbout() { return this.about; }
        public String getRiddle() { return this.riddle; }
        public String getReturnDialog() { return this.returnDialog; }
        public String getAnswer() { return this.answer; }
        public String getItem() { return this.item; }

        public NPC(String name, List<String> npc_image, String about, String item, String riddle, String answer, String returnDialog) {
            this.name = name;
            this.image = npc_image;
            this.about = about;
            this.item = item;
            this.riddle = riddle;
            this.answer = answer;
            this.returnDialog = returnDialog;
        }
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public NPC getNPC() { return npc; }
    public List<String> getNeighbours() { return neighbours; }

    public Location(String name, String desc, String npc_name, List<String> npc_image, String npc_about, String npc_item,
                    String npc_riddle, String npc_answer, String npc_returnDialog, List<String> neigh) {
        this.name = name;
        this.description = desc;
        this.npc = new NPC(npc_name, npc_image, npc_about, npc_item, npc_riddle, npc_answer, npc_returnDialog);
        this.neighbours = neigh;
    }

}
