package cz.rockpoint.utils.Impl;

import com.google.common.collect.Lists;
import cz.rockpoint.model.CategoryEntity;
import cz.rockpoint.model.CompetitionEntity;
import cz.rockpoint.model.PersonEntity;
import cz.rockpoint.model.TeamEntity;
import cz.rockpoint.utils.IParser;
import lombok.Getter;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Collections;

// IParserImpl class deals with receiving and processing information from the source
@Component
public class IParserImpl implements IParser {

    // Variable for Race / Competition ID
    @Getter
    private int URLid;

    // Method receives only table from URL
    @Override
    public Elements getTableDataFromURL(String URL) {
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(URL);
            CloseableHttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            String dataString = IOUtils.toString(entity.getContent(), "UTF-8");
            EntityUtils.consume(entity);

            if (dataString != null) {
                Document doc = Jsoup.parse(dataString);
                Elements tdS = doc.getElementsByTag("td");
                return (tdS == null) ? null : tdS;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    // Method updates all information from Long category table
    @Override
    public void updateDataFromLong() {
        Elements tdS = getTableDataFromURL("http://85.118.133.203/race.php?id=" + this.URLid + "&distance=long");

        // Check for checkpoints count (for 4 CHPs - 14)
        int iterator = 14;

        // In case we have 5 CHP
        if (!tdS.get(14).text().equals("2"))
            iterator = 15;

        // Necessary variables
        String category_title;
        String team_title;
        String competition_finish;
        String competition_chp1;
        String competition_chp2;
        String competition_chp3;
        String competition_chp4;
        String competition_chp5;
        String participant_fname;
        String participant_lname;
        Integer participant1_no;
        Integer participant2_no;
        Integer competition_place;

        for (int i = 0; i < tdS.size(); i += iterator) {
            try {
                // Category
                category_title = tdS.get(i + 2).text();
                CategoryEntity category = getCategoryByByTitle(category_title);
                if (category == null) category = new CategoryEntity(category_title);

                // Participant no. 1
                participant1_no = Integer.parseInt(tdS.get(i + 3).text());
                PersonEntity participant1 = getPersonByNo(participant1_no);

                if (participant1 == null) {
                    participant_fname = tdS.get(i + 4).text();
                    participant_lname = tdS.get(i + 5).text();
                    participant1 = new PersonEntity(participant1_no, participant_fname, participant_lname);
                }

                // Team
                team_title = tdS.get(i + 1).text();
                TeamEntity competition_team = getTeamByParticipant(participant1);
                if (competition_team == null)
                    competition_team = new TeamEntity(team_title, category, Collections.singletonList(participant1));

                // Competition
                competition_place = Integer.parseInt(tdS.get(i).text());
                CompetitionEntity competition = getCompetitionByParticipantAndType(participant1, 0);

                if (competition == null) {
                    competition = new CompetitionEntity();
                    competition.setId();
                    competition.setType(0);
                    competition.setTeam(competition_team);
                }

                competition.setPlace(competition_place);
                competition_finish = tdS.get(i + 9).text();
                competition_chp1 = tdS.get(i + 10).text();
                competition_chp2 = tdS.get(i + 11).text();
                competition_chp3 = tdS.get(i + 12).text();
                competition_chp4 = tdS.get(i + 13).text();
                competition.setFinish(checkTime(competition_finish));
                competition.setCheckpoints(Lists.newArrayList(
                        checkTime(competition_chp1),
                        checkTime(competition_chp2),
                        checkTime(competition_chp3),
                        checkTime(competition_chp4)));

                // If we have 5 checkpoints - add fifth CHP to competition checkpoints list.
                if (iterator != 14) {
                    competition_chp5 = tdS.get(i + 14).text();
                    competition.setCheckpoints(Arrays.asList(
                            checkTime(competition_chp1),
                            checkTime(competition_chp2),
                            checkTime(competition_chp3),
                            checkTime(competition_chp4),
                            checkTime(competition_chp5)));
                }

                // Participant no. 2 [Only if table has one]
                participant2_no = Integer.parseInt(tdS.get(i + 6).text());

                if (participant2_no != 0) {
                    participant_fname = tdS.get(i + 7).text();
                    participant_lname = tdS.get(i + 8).text();
                    PersonEntity participant2 = new PersonEntity(participant2_no, participant_fname, participant_lname);
                    competition_team.setParticipants(Arrays.asList(participant1, participant2));
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    // Method updates all information from Half category table
    @Override
    public void updateDataFromHalf() {
        Elements tdS = getTableDataFromURL("http://85.118.133.203/race.php?id=" + this.URLid + "&distance=half");

        // Check for checkpoints count (for 4 CHPs - 14)
        int iterator = 14;

        // Necessary variables
        String category_title;
        String team_title;
        String competition_finish;
        String competition_chp1;
        String competition_chp2;
        String competition_chp3;
        String competition_chp4;
        String participant_fname;
        String participant_lname;
        Integer participant1_no;
        Integer competition_place;

        for (int i = 0; i < tdS.size(); i += iterator) {
            try {
                // Category
                category_title = tdS.get(i + 2).text();
                CategoryEntity category = getCategoryByByTitle(category_title);
                if (category == null) category = new CategoryEntity(category_title);

                // Participant no. 1
                participant1_no = Integer.parseInt(tdS.get(i + 3).text());
                PersonEntity participant1 = getPersonByNo(participant1_no);

                if (participant1 == null) {
                    participant_fname = tdS.get(i + 4).text();
                    participant_lname = tdS.get(i + 5).text();
                    participant1 = new PersonEntity(participant1_no, participant_fname, participant_lname);
                }

                // Team
                team_title = "";
                TeamEntity competition_team = getTeamByParticipant(participant1);
                if (competition_team == null)
                    competition_team = new TeamEntity(team_title, category, Collections.singletonList(participant1));

                // Competition
                competition_place = Integer.parseInt(tdS.get(i).text());
                CompetitionEntity competition = getCompetitionByParticipantAndType(participant1, 1);

                if (competition == null) {
                    competition = new CompetitionEntity();
                    competition.setId();
                    competition.setType(1);
                    competition.setTeam(competition_team);
                }

                competition.setPlace(competition_place);
                competition_finish = tdS.get(i + 9).text();
                competition_chp1 = tdS.get(i + 10).text();
                competition_chp2 = tdS.get(i + 11).text();
                competition_chp3 = tdS.get(i + 12).text();
                competition_chp4 = tdS.get(i + 13).text();
                competition.setFinish(checkTime(competition_finish));
                competition.setCheckpoints(Lists.newArrayList(
                        checkTime(competition_chp1),
                        checkTime(competition_chp2),
                        checkTime(competition_chp3),
                        checkTime(competition_chp4)));

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    // Method updates all information from Short category table
    @Override
    public void updateDataFromShort() {
        Elements tdS = getTableDataFromURL("http://85.118.133.203/race.php?id=" + this.URLid + "&distance=short");

        // Check for checkpoints count (for 4 CHPs - 14)
        int iterator = 14;

        // Necessary variables
        String category_title;
        String team_title;
        String competition_finish;
        String competition_chp1;
        String competition_chp2;
        String competition_chp3;
        String competition_chp4;
        String participant_fname;
        String participant_lname;
        Integer participant1_no;
        Integer competition_place;

        for (int i = 0; i < tdS.size(); i += iterator) {
            try {
                // Category
                category_title = tdS.get(i + 2).text();
                CategoryEntity category = getCategoryByByTitle(category_title);
                if (category == null) category = new CategoryEntity(category_title);

                // Participant no. 1
                participant1_no = Integer.parseInt(tdS.get(i + 3).text());
                PersonEntity participant1 = getPersonByNo(participant1_no);

                // If this Participant no. 1 is already in the PersonEntity total list - we skip the whole competition line.
                // If Participant no. 1 in not in the PersonEntity total list - continue parsing
                if (participant1 == null) {
                    participant_fname = tdS.get(i + 4).text();
                    participant_lname = tdS.get(i + 5).text();
                    participant1 = new PersonEntity(participant1_no, participant_fname, participant_lname);
                }

                // Team
                team_title = "";
                TeamEntity competition_team = getTeamByParticipant(participant1);
                if(competition_team == null) competition_team = new TeamEntity(team_title, category, Collections.singletonList(participant1));

                // Competition
                competition_place = Integer.parseInt(tdS.get(i).text());
                CompetitionEntity competition = getCompetitionByParticipantAndType(participant1, 2);

                if (competition == null) {
                    competition = new CompetitionEntity();
                    competition.setId();
                    competition.setType(2);
                    competition.setTeam(competition_team);
                }

                competition.setPlace(competition_place);
                competition_finish = tdS.get(i + 9).text();
                competition_chp1 = tdS.get(i + 10).text();
                competition_chp2 = tdS.get(i + 11).text();
                competition_chp3 = tdS.get(i + 12).text();
                competition_chp4 = tdS.get(i + 13).text();
                competition.setFinish(checkTime(competition_finish));
                competition.setCheckpoints(Lists.newArrayList(
                        checkTime(competition_chp1),
                        checkTime(competition_chp2),
                        checkTime(competition_chp3),
                        checkTime(competition_chp4)));

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    // Method updates all information from all tables
    @Override
    public void updateDataFromAllURLs() {
        updateDataFromLong();
        updateDataFromHalf();
        updateDataFromShort();
    }

    // Method updates URLid of the Race and calls method clearEntities()
    @Override
    public void updateURL(int URLid) {
        this.URLid = URLid;
        clearEntities();
    }

    // Method clears all information from entities lists
    @Override
    public void clearEntities() {
        CategoryEntity.getCategories().clear();
        CategoryEntity.setIdentifier(0);

        PersonEntity.getPersons().clear();
        PersonEntity.setIdentifier(0);

        CompetitionEntity.getCompetitions().clear();
        CompetitionEntity.setIdentifier(0);

        TeamEntity.getTeams().clear();
        TeamEntity.setIdentifier(0);
    }

    // Method checks string with time
    private LocalTime checkTime(String time) throws ParseException {
        DateTimeFormatter dtf = DateTimeFormat.forPattern("mm:ss:SS");
        if (time.isEmpty()) return null;
        else return LocalTime.parse(time, dtf);
    }

    // Methods getEntities by parameters
    private CategoryEntity getCategoryByByTitle(String title) {
        return CategoryEntity.getCategories().stream().filter(c -> c.getTitle().equals(title)).findFirst().orElse(null);
    }

    private PersonEntity getPersonByNo(int no) {
        return PersonEntity.getPersons().stream().filter(p -> p.getNo() == no).findFirst().orElse(null);
    }

    private TeamEntity getTeamByParticipant(PersonEntity personEntity) {
        return TeamEntity.getTeams().stream().filter(t -> t.getParticipants().stream().anyMatch(p -> p.equals(personEntity))).findFirst().orElse(null);
    }

    private CompetitionEntity getCompetitionByParticipantAndType(PersonEntity personEntity, int type) {
        return CompetitionEntity.getCompetitions().stream().filter(c -> c.getType() == type && c.getTeam().getParticipants().contains(personEntity)).findFirst().orElse(null);
    }
}
