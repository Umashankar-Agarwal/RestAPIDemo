public class PayloadGraphQL {

    public static String queryPayload(int characterID, int locationId, int EpisodeID) {
        return "{\"query\":\"query($characterId : Int! ,$locationId : Int! , $episodeId : Int! ){\\n  character(characterId :$characterId){\\n    name, status, gender ,type, id,  location{\\n      name, type\\n    }\\n  }\\n    \\n    location(locationId: $locationId){\\n      name, type, dimension, id\\n    }\\n    \\n  episode (episodeId : $episodeId){\\n    id, name, air_date , characters{\\n      name\\n    }\\n  }\\n}\\n\\n\\n\",\"variables\":{\"characterId\":" + characterID + ",\"locationId\":" + locationId + ",\"episodeId\":" + EpisodeID + "}}";
    }

    public static String mutationPayload(String characterName , String locationaName , String episodeName){
        return "{\"query\":\"mutation($Locationname : String! , $charactername : String! , $Episodename : String ! ){\\n  \\n  createLocation (location:{name : $Locationname , type : \\\"South\\\" , dimension : \\\"67812\\\"} ){\\n    id\\n  }\\n  \\n  createCharacter(character: {name : $charactername, type : \\\"Male actor\\\" , gender : \\\"Male\\\"\\n    status : \\\"alive\\\", species : \\\"Indain\\\", image : \\\"123.png\\\", originId : 31389, locationId : 31389}){\\n    id\\n  }\\n  \\n  createEpisode(episode : {name : $Episodename, air_date : \\\"10th June 2026\\\", episode : \\\"Season 1\\\"} )\\n  {\\n    id\\n  }\\n  \\n  deleteLocations (locationIds : [31391, 31392, 31393]){\\n    locationsDeleted\\n  }\\n  \\n}\\n\\n\\n\",\"variables\":{\"charactername\":\""+characterName+"\",\"Locationname\":\""+locationaName+"\",\"Episodename\":\""+episodeName+"\"}}";
    }
}
